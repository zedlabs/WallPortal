package tk.zedlabs.wallaperapp2019

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class ImageDetailViewModel(applicationContext : Context) : ViewModel() {

     private val fileUtils : FileUtils = FileUtils(viewModelScope,applicationContext)

    fun downloadImage(bitmap : Bitmap,id : String){
        fileUtils.saveImage(bitmap,id)
    }
    fun setWallpaper(image: Bitmap, id: String){
        fileUtils.setWallpaper1(image,id)
    }
}