package tk.zedlabs.wallportal.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.activity.DetailActivity
import tk.zedlabs.wallportal.util.MainAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

@AndroidEntryPoint
class NewFragment : Fragment(), MainAdapter.OnImageListener {

    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: GridLayoutManager
    private lateinit var postViewModel: PostViewModel

    override fun onImageClick(position: Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        val imageDetails = postViewModel.postPagedList?.value?.get(position)
        val urlFull = imageDetails?.path
        val urlRegular = imageDetails?.thumbs?.original
        val id = imageDetails?.id
        intent.putExtra("url_large", urlFull)
        intent.putExtra("url_regular", urlRegular)
        intent.putExtra("id", id)
        intent.putExtra("Activity", "NewActivity")

        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_new, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireContext().isConnectedToNetwork()) {
            true -> if (textViewConnectivity.visibility == VISIBLE) textViewConnectivity.visibility = GONE
            false -> textViewConnectivity.visibility = VISIBLE
        }

        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        postViewModel.postPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewManager = GridLayoutManager(this.context, 2)
        viewAdapter = MainAdapter(this)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
