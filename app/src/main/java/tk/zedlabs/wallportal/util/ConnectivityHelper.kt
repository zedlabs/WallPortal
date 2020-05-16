package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityHelper(private val context: Context) {

    fun isConnectedToNetwork(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}