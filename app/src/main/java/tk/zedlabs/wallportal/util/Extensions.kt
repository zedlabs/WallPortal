package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_image_details.*

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