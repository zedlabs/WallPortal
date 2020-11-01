package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.databinding.FragmentPopularBinding
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.MainAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

@AndroidEntryPoint
class PopularFragment : Fragment(), MainAdapter.OnImageListener {

    private lateinit var viewAdapter: MainAdapter
    private val postViewModel: PostViewModel by viewModels()

    private var _binding: FragmentPopularBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onImageClick(position: Int) {
        val bookmarkImage = BookmarkImage(
            imageName = postViewModel.popularPagedList?.value?.get(position)?.id.toString(),
            imageUrlFull = postViewModel.popularPagedList?.value?.get(position)?.path,
            imageUrlRegular = postViewModel.popularPagedList?.value?.get(position)?.thumbs?.small
        )
            findNavController().navigate(
                PopularFragmentDirections.actionPopularBottomToDetailActivity(
                    bookmarkImage,
                    "PopularActivity"
                )
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (context?.isConnectedToNetwork()) {
            true -> if (binding.textViewConnectivityPop.visibility == VISIBLE) binding.textViewConnectivityPop.visibility =
                GONE
            false -> binding.textViewConnectivityPop.visibility = VISIBLE
        }

        postViewModel.popularPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewAdapter = MainAdapter(this)
        binding.recyclerViewPopular.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = viewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
