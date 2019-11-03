package tk.zedlabs.wallaperapp2019.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_new.*
import tk.zedlabs.wallaperapp2019.MainAdapter
import tk.zedlabs.wallaperapp2019.PostViewModel
import tk.zedlabs.wallaperapp2019.R

class NewFragment : Fragment(),MainAdapter.OnImageListener{

    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: GridLayoutManager
    private lateinit var postViewModel : PostViewModel

    override fun onImageClick(position: Int) {
        val intent = Intent(activity ,ImageDetails::class.java)
        val imageDetails = postViewModel.postPagedList?.value?.get(position)
        val urlFull = imageDetails?.urls?.full
        val urlRegular = imageDetails?.urls?.regular
        val id = imageDetails?.id
        intent.putExtra("url_large", urlFull)
        intent.putExtra("url_regular", urlRegular)
        intent.putExtra("id", id)

        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_new, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        postViewModel.postPagedList?.observe(viewLifecycleOwner, Observer { postList ->
            viewAdapter.submitList(postList)
        })
        viewManager = GridLayoutManager(this.context,2)
        viewAdapter = MainAdapter(this)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
