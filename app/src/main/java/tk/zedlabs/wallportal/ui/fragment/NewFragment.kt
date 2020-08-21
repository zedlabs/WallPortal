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
import kotlinx.android.synthetic.main.fragment_new.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.repository.BookmarkImage
import tk.zedlabs.wallportal.util.MainAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

@AndroidEntryPoint
class NewFragment : Fragment(), MainAdapter.OnImageListener {

    private lateinit var viewAdapter: MainAdapter
    private val postViewModel: PostViewModel by viewModels()

    override fun onImageClick(position: Int) {
        val bi = BookmarkImage(
            imageName = postViewModel.postPagedList?.value?.get(position)?.id.toString(),
            imageUrlFull = postViewModel.postPagedList?.value?.get(position)?.path,
            imageUrlRegular = postViewModel.postPagedList?.value?.get(position)?.thumbs?.small
        )
        val action = NewFragmentDirections.actionNewBottomToDetailActivity(bi, "NewActivity")
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireContext().isConnectedToNetwork()) {
            true -> if (textViewConnectivity.visibility == VISIBLE)
                textViewConnectivity.visibility = GONE
            false -> textViewConnectivity.visibility = VISIBLE
        }
        postViewModel.postPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewAdapter = MainAdapter(this)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = viewAdapter
        }
    }
}
