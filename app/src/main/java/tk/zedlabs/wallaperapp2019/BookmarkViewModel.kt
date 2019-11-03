package tk.zedlabs.wallaperapp2019

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class BookmarkViewModel(private val bookmarksDao: BookmarkDao) : ViewModel() {

    fun getBookMarkImages() : List<BookmarkImage>{
        return bookmarksDao.getAll()
    }
}