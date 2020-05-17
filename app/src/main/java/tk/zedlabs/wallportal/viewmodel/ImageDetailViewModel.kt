package tk.zedlabs.wallportal.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import tk.zedlabs.wallportal.models.Data
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils

class ImageDetailViewModel(
    applicationContext: Context,
    private val bookmarksDao: BookmarkDao
) : ViewModel() {

    private val fileUtils: FileUtils = FileUtils(viewModelScope, applicationContext)
    val repository: ImageDetailsRepository = ImageDetailsRepository()

    val isBookmark = MutableLiveData<Boolean>().apply { this.value = false }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    suspend fun getImageDetails(id: String): Response<Data> {
        return repository.getData(id)
    }

    fun checkIsBookmark(imageUrl: String) {
        viewModelScope.launch {
            val bookmarks = bookmarksDao.getAll()
            bookmarks.map {
                if (it.imageUrlRegular == imageUrl) isBookmark.postValue(true)
            }
        }
    }
}