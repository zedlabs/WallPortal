package tk.zedlabs.wallaperapp2019.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.activity_image_details.*
import kotlinx.android.synthetic.main.fab_image_details.*
import tk.zedlabs.wallaperapp2019.R

class ImageDetails : AppCompatActivity() {

    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabClock: Animation? = null
    private var fabAntiClock: Animation? = null
    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        val intent = intent
        val urlLarge = intent.getStringExtra("url_large")

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fabClock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate)
        fabAntiClock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_anti)

        fab?.setOnClickListener {
            if (isOpen) {
                textview_mail.visibility = View.INVISIBLE
                textview_share.visibility = View.INVISIBLE
                fab2.startAnimation(fabClose)
                fab1.startAnimation(fabClose)
                fab.startAnimation(fabAntiClock)
                fab2.isClickable = false
                fab1.isClickable = false
                isOpen = false
            } else {
                textview_mail.visibility = View.VISIBLE
                textview_share.visibility = View.VISIBLE
                fab2.startAnimation(fabOpen)
                fab1.startAnimation(fabOpen)
                fab.startAnimation(fabClock)
                fab2.isClickable = true
                fab1.isClickable = true
                isOpen = true
            }
        }
        Glide.with(this)
            .load(urlLarge)
            .transform(CenterCrop())
            .placeholder(circularProgressDrawable)
            .into(photo_view)
    }
}
