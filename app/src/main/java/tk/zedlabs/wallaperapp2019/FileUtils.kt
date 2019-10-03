package tk.zedlabs.wallaperapp2019

import android.Manifest
import android.os.Environment
import android.util.Log
import java.io.File
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build

class FileUtils {
    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    fun getPublicAlbumStorageDir(albumName: String): File? {
        // Get the directory for the user's public pictures directory.
        val file = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), albumName)
        if (!file?.mkdirs()) {
            Log.e("File Creation: ", "Directory not created")
        }
        return file
    }

}