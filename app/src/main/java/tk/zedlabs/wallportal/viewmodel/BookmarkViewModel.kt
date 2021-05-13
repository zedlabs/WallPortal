package tk.zedlabs.wallportal.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.persistence.BookmarkDao
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils
import tk.zedlabs.wallportal.util.Resource
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val fileUtils: FileUtils,
    private val bookmarksDao: BookmarkDao,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    val isBookmark: MutableLiveData<Boolean> = MutableLiveData(false)
    val bookmarkList = mutableStateOf<List<BookmarkImage>>(listOf())
    val loading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            bookmarkList.value = bookmarksDao.getAll().asReversed()
        }
    }

    fun getDetails(id: String) {
        viewModelScope.launch {
            repository.getWallpaperData(id)
        }
    }

    suspend fun getImageDetails(id: String): Resource<ImageDetails> {
        return repository.getWallpaperData(id)
    }

    fun setBookmark(item: ImageDetails) {
        isBookmark.value = true
        viewModelScope.launch {
            bookmarksDao.insert(
                BookmarkImage(
                    imageName = item.id1!!,
                    imageUrlFull = item.path1,
                    imageUrlRegular = item.thumbs?.small
                )
            )
        }
    }

    fun checkBookmark(name: String) {
        viewModelScope.launch {
            if (bookmarksDao.getItemByName(name).isNotEmpty()) isBookmark.value = true
        }
    }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    suspend fun delete(bookmarkImage: BookmarkImage) {
        bookmarksDao.delete(bookmarkImage)
    }
}