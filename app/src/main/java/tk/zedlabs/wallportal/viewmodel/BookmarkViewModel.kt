package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.ViewModel
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.BookmarkImage

class BookmarkViewModel(private val bookmarksDao: BookmarkDao) : ViewModel() {

    suspend fun getBookMarkImages(): List<BookmarkImage> {
        return bookmarksDao.getAll()
    }
}