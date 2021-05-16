package tk.zedlabs.wallportal.ui.fragment

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.zedlabs.wallportal.models.WallHavenResponse
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils
import tk.zedlabs.wallportal.util.Resource
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val fileUtils: FileUtils,
    private val repository: ImageDetailsRepository
) : ViewModel() {

    val isBookmark: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading = mutableStateOf(false)
    val bookmarkList: LiveData<List<BookmarkImage>> = repository.getBookmarks().asLiveData()

    suspend fun getImageDetails(id: String): Resource<WallHavenResponse> {
        return repository.getWallpaperData(id)
    }

    fun setBookmark(item: WallHavenResponse) {
        isBookmark.value = true
        viewModelScope.launch {
            repository.setBookmark(item)
        }
    }

    fun checkBookmark(id: String){
        viewModelScope.launch {
            if (repository.checkBookmark(id)) isBookmark.value = true
        }
    }

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    fun deleteBookmark(id: String){
        isBookmark.value = false
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }
}