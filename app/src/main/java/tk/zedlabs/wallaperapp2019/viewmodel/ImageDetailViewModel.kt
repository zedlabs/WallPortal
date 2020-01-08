package tk.zedlabs.wallaperapp2019.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tk.zedlabs.wallaperapp2019.util.FileUtils

class ImageDetailViewModel(applicationContext : Context) : ViewModel() {

     private val fileUtils : FileUtils =
         FileUtils(
             viewModelScope,
             applicationContext
         )

    fun downloadImage(bitmap : Bitmap,id : String){
    fileUtils.saveImage(bitmap,id)
}
}