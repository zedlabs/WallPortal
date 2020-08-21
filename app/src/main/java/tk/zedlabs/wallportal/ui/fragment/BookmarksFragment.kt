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
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.repository.BookmarkImage
import tk.zedlabs.wallportal.util.BaseFragment
import tk.zedlabs.wallportal.util.BookmarkAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel

@AndroidEntryPoint
class BookmarksFragment : BaseFragment(), BookmarkAdapter.OnImageListener {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var list: List<BookmarkImage>

    override fun onImageClick(position: Int) {
        val action = BookmarksFragmentDirections.actionBookmarksBottomToDetailActivity(list[position], "BookmarkActivity")
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_saved, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (context?.isConnectedToNetwork()) {
            true -> if (textViewConnectivityBookmark.visibility == VISIBLE) textViewConnectivityBookmark.visibility = GONE
            false -> textViewConnectivityBookmark.visibility = VISIBLE
        }
        //todo move coroutine to Vm and maybe need to move to onResume or add swipe refresh layout
        launch {
            context?.let {
                list = bookmarkViewModel.getBookMarkImages().asReversed()
                if (list.isNullOrEmpty()) textViewEmpty.visibility = VISIBLE
            }
            recyclerViewBookmarked.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                adapter = BookmarkAdapter(list, this@BookmarksFragment)
            }
        }
    }
}
