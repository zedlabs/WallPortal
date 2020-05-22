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
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.activity.DetailActivity
import tk.zedlabs.wallportal.util.MainAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.PostViewModel

class PopularFragment : Fragment(), MainAdapter.OnImageListener {

    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: GridLayoutManager
    private lateinit var postViewModel: PostViewModel

    override fun onImageClick(position: Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        val imageDetails = postViewModel.popularPagedList?.value?.get(position)
        val urlFull = imageDetails?.path
        val urlRegular = imageDetails?.thumbs?.original
        val id = imageDetails?.id
        intent.putExtra("url_regular", urlRegular)
        intent.putExtra("id", id)
        intent.putExtra("url_large", urlFull)
        intent.putExtra("Activity", "PopularActivity")
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (context?.isConnectedToNetwork()) {
            true -> if (textViewConnectivityPop.visibility == VISIBLE) textViewConnectivityPop.visibility = GONE
            false -> textViewConnectivityPop.visibility = VISIBLE
        }

        postViewModel = getViewModel { parametersOf() }
        postViewModel.popularPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewManager = GridLayoutManager(this.context, 2)
        viewAdapter = MainAdapter(this)
        recyclerViewPopular.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
