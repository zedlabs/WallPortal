package tk.zedlabs.wallportal.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tk.zedlabs.wallportal.persistence.BookmarkDao
import tk.zedlabs.wallportal.persistence.BookmarkImage
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarksDao: BookmarkDao
) : ViewModel() {

    suspend fun getBookMarkImages(): List<BookmarkImage> {
        return bookmarksDao.getAll()
    }

    suspend fun getIdList(): List<String>{
        return bookmarksDao.getId()
    }

    suspend fun insertBookMarkImage(bookmarkImage: BookmarkImage){
        bookmarksDao.insert(bookmarkImage)
    }

    suspend fun delete(bookmarkImage: BookmarkImage){
        bookmarksDao.delete(bookmarkImage)
    }
}