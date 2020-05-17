package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

fun Context.isConnectedToNetwork(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return manager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

fun Context.shortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()