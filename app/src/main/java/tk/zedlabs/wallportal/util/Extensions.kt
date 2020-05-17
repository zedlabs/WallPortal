package tk.zedlabs.wallportal.util

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

fun Context.shortToast(message: String) = Toast.makeText(this, message, LENGTH_SHORT).show()