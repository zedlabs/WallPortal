package tk.zedlabs.wallportal.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityHelper(context: Context) {

    val mContext = context
    fun isConnectedToNetwork(): Boolean {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}