package tk.zedlabs.wallportal.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import tk.zedlabs.wallportal.MyApplication
import tk.zedlabs.wallportal.models.Data
import tk.zedlabs.wallportal.repository.BookmarkDao
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils

class ImageDetailViewModel(
    app: Application,
    private val bookmarksDao: BookmarkDao
) : AndroidViewModel(app) {

    private val fileUtils: FileUtils = FileUtils(getApplication<MyApplication>())
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