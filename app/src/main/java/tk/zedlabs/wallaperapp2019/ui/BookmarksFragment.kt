package tk.zedlabs.wallaperapp2019.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.coroutines.launch
import tk.zedlabs.wallaperapp2019.*

class BookmarksFragment : BaseFragment() {

    private lateinit var viewAdapter: BookmarkAdapter
    private lateinit var viewManager: GridLayoutManager
    private lateinit var bookmarkViewModel : BookmarkViewModel
    private lateinit var list : List<BookmarkImage>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Room.databaseBuilder(
            activity!!.applicationContext,
            BookmarkDatabase::class.java, "bookmark-database"
        ).build()
        //bookmarkViewModel = ViewModelProviders.of(this).get(BookmarkViewModel()::class.java)
        bookmarkViewModel = BookmarkViewModel(db.bookmarkDao())
        viewManager = GridLayoutManager(this.context,2)
        launch {
            context?.let {
                list =  bookmarkViewModel.getBookMarkImages()
                viewAdapter = BookmarkAdapter(list)
            }
            recyclerViewBookmarked.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
           }


    }
}
