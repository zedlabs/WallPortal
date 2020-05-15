package tk.zedlabs.wallportal.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Response
import tk.zedlabs.wallportal.models.Data
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.repository.ImageDetailsRepository
import tk.zedlabs.wallportal.util.FileUtils

class ImageDetailViewModel(applicationContext: Context) : ViewModel() {

    private val fileUtils: FileUtils = FileUtils(viewModelScope, applicationContext)
    val repository : ImageDetailsRepository = ImageDetailsRepository()

    fun downloadImage(bitmap: Bitmap, id: String) {
        fileUtils.saveImage(bitmap, id)
    }

    suspend fun getImageDetails(id: String): Response<Data>{
        return repository.getData(id)
    }
}