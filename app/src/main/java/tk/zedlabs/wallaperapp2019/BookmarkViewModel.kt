package tk.zedlabs.wallaperapp2019

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class BookmarkViewModel(private val bookmarksDao: BookmarkDao) : ViewModel() {

   suspend fun getBookMarkImages() : List<BookmarkImage>{
        return bookmarksDao.getAll()
    }
}