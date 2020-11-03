package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.databinding.FragmentSavedBinding
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.BaseFragment
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel

@AndroidEntryPoint
class BookmarksFragment : BaseFragment(), BookmarkAdapter.OnImageListener {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var list: List<BookmarkImage>

    private var _binding: FragmentSavedBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onImageClick(position: Int) {

        findNavController().navigate(
            BookmarksFragmentDirections.actionBookmarksBottomToDetailActivity(
                list[position],
                "BookmarkActivity"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (context?.isConnectedToNetwork()) {
            true -> if (binding.textViewConnectivityBookmark.visibility == VISIBLE)
                binding.textViewConnectivityBookmark.visibility = GONE
            false -> binding.textViewConnectivityBookmark.visibility = VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        launch {
            context?.let {
                list = bookmarkViewModel.getBookMarkImages().asReversed()
                if (list.isNullOrEmpty()) binding.textViewEmpty.visibility = VISIBLE
            }
            binding.recyclerViewBookmarked.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                adapter = BookmarkAdapter(list, this@BookmarksFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
