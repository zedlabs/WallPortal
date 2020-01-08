package tk.zedlabs.wallaperapp2019.viewmodel

import androidx.lifecycle.ViewModel
import tk.zedlabs.wallaperapp2019.repository.BookmarkDao
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage

class BookmarkViewModel(private val bookmarksDao: BookmarkDao) : ViewModel() {

   suspend fun getBookMarkImages() : List<BookmarkImage>{
        return bookmarksDao.getAll()
    }
}