package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.ViewModel
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.BookmarkImage
import javax.inject.Inject

class BookmarkViewModel @Inject
    constructor(private val bookmarksDao: BookmarkDao) : ViewModel() {

    suspend fun getBookMarkImages(): List<BookmarkImage> {
        return bookmarksDao.getAll()
    }
}