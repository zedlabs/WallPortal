package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_image_details.*
import tk.zedlabs.wallportal.BuildConfig
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.fragment.DetailFragment
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

fun Context.showSnackbar(parent: View, removeListener: DetailFragment.RemoveListener) {
    Snackbar.make(
        parent,
        getString(R.string.remove_from_bookmarks_qm),
        Snackbar.LENGTH_LONG
    )
        .setAction(getString(R.string.remove_string), removeListener)
        .setActionTextColor(
            ContextCompat.getColor(this, R.color.aquamarine)
        )
        .show()
}

fun Context.getUriForId(id: String): Uri =
    FileProvider.getUriForFile(
        this, BuildConfig.APPLICATION_ID + ".fileprovider",
        File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
    )

