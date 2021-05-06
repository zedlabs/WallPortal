package tk.zedlabs.wallportal.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.persistence.BookmarkDao
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val fileUtils: FileUtils,
    private val bookmarksDao: BookmarkDao,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    val isBookmark: MutableLiveData<Boolean> = MutableLiveData(false)
    val imageDetails: MutableLiveData<ImageDetails> = MutableLiveData()

    suspend fun getBookMarkImages(): List<BookmarkImage> {
        return bookmarksDao.getAll()
    }

    fun setBookmark(item: BookmarkImage) {
        isBookmark.value = true
        viewModelScope.launch {
            bookmarksDao.insert(item)
        }
    }

    fun checkBookmark(name: String) {
        viewModelScope.launch {
            if (bookmarksDao.getItemByName(name).isNotEmpty()) isBookmark.value = true
            imageDetails.value = repository.getData(name)
        }
    }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    suspend fun delete(bookmarkImage: BookmarkImage) {
        bookmarksDao.delete(bookmarkImage)
    }
}