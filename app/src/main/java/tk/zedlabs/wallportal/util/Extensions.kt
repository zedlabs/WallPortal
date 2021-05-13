package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.FileProvider
import androidx.transition.TransitionManager
import tk.zedlabs.wallportal.BuildConfig
import java.io.File

fun Context.isConnectedToNetwork(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return manager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

fun Context.shortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()

fun ViewGroup.makeFadeTransition(animationDuration: Long) {
    val fade: androidx.transition.Fade = androidx.transition.Fade()

    fade.apply {
        duration = animationDuration
    }
    TransitionManager.beginDelayedTransition(this, fade)
}


fun Context.getUriForId(id: String): Uri =
    FileProvider.getUriForFile(
        this, BuildConfig.APPLICATION_ID + ".fileprovider",
        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
    )

