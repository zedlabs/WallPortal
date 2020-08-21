package tk.zedlabs.wallportal.viewmodel

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import tk.zedlabs.wallportal.models.Data
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils

class ImageDetailViewModel @ViewModelInject constructor(
    private val fileUtils: FileUtils,
    private val bookmarksDao: BookmarkDao,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    val isBookmark = MutableLiveData<Boolean>().apply { this.value = false }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    //add caching in the repository function
    suspend fun getImageDetails(id: String): Response<Data> {
        return repository.getData(id)
    }

    //move to repository and fetch from there
    fun checkIsBookmark(imageUrl: String) {
        viewModelScope.launch {
            val bookmarks = bookmarksDao.getAll()
            bookmarks.map {
                if (it.imageUrlRegular == imageUrl) isBookmark.postValue(true)
            }
        }
    }
}