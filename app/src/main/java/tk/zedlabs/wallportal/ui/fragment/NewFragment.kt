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
import tk.zedlabs.wallportal.databinding.FragmentNewBinding
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.MainAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

@AndroidEntryPoint
class NewFragment : Fragment(), MainAdapter.OnImageListener {

    private lateinit var viewAdapter: MainAdapter
    private val postViewModel: PostViewModel by viewModels()

    private var _binding: FragmentNewBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onImageClick(position: Int) {
        val bookmarkImage = BookmarkImage(
            imageName = postViewModel.postPagedList?.value?.get(position)?.id.toString(),
            imageUrlFull = postViewModel.postPagedList?.value?.get(position)?.path,
            imageUrlRegular = postViewModel.postPagedList?.value?.get(position)?.thumbs?.small
        )
        findNavController().navigate(
            NewFragmentDirections.actionNewBottomToDetailActivity(
                bookmarkImage,
                "NewActivity"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireContext().isConnectedToNetwork()) {
            true -> if (binding.textViewConnectivity.visibility == VISIBLE)
                binding.textViewConnectivity.visibility = GONE
            false -> binding.textViewConnectivity.visibility = VISIBLE
        }
        postViewModel.postPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewAdapter = MainAdapter(this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = viewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
