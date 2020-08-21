package tk.zedlabs.wallportal.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.repository.BookmarkDatabase
import tk.zedlabs.wallportal.repository.BookmarkImage
import tk.zedlabs.wallportal.ui.activity.DetailActivity
import tk.zedlabs.wallportal.util.BaseFragment
import tk.zedlabs.wallportal.util.BookmarkAdapter
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel

@AndroidEntryPoint
class BookmarksFragment : BaseFragment(), BookmarkAdapter.OnImageListener {

    private lateinit var viewAdapter: BookmarkAdapter
    private lateinit var viewManager: GridLayoutManager
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var list: List<BookmarkImage>

    override fun onImageClick(position: Int) {
        //todo navigation args
        val intent = Intent(activity, DetailActivity::class.java)
        val imageDetails = list[position]
        val urlFull = imageDetails.imageUrlFull
        val urlRegular = imageDetails.imageUrlRegular
        val id = imageDetails.imageName
        intent.putExtra("url_large", urlFull)
        intent.putExtra("url_regular", urlRegular)
        intent.putExtra("id", id)
        intent.putExtra("Activity", "BookmarkActivity")
        startActivity(intent)
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

    override fun onResume() {
        //todo move to vm
        super.onResume()
        //bookmarkViewModel = BookmarkViewModel(db.bookmarkDao())
        //viewManager =


    }
}
