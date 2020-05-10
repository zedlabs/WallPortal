package tk.zedlabs.wallaperapp2019.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.coroutines.launch
import tk.zedlabs.wallaperapp2019.*
import tk.zedlabs.wallaperapp2019.repository.BookmarkDatabase
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage
import tk.zedlabs.wallaperapp2019.util.BaseFragment
import tk.zedlabs.wallaperapp2019.util.BookmarkAdapter
import tk.zedlabs.wallaperapp2019.viewmodel.BookmarkViewModel

class BookmarksFragment : BaseFragment(), BookmarkAdapter.OnImageListener {

    private lateinit var viewAdapter: BookmarkAdapter
    private lateinit var viewManager: GridLayoutManager
    private lateinit var bookmarkViewModel : BookmarkViewModel
    private lateinit var list : List<BookmarkImage>

    override fun onImageClick(position: Int) {
        val intent = Intent(activity ,Main2Activity::class.java)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Room.databaseBuilder(
            requireActivity().applicationContext,
            BookmarkDatabase::class.java, "bookmark-database"
        ).build()

        bookmarkViewModel =BookmarkViewModel(db.bookmarkDao())
        viewManager = GridLayoutManager(this.context,2)

        launch {
            context?.let {
                list =  bookmarkViewModel.getBookMarkImages().asReversed()
                viewAdapter = BookmarkAdapter(list,this@BookmarksFragment)
            }
            recyclerViewBookmarked.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
           }


    }
}
