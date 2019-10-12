package tk.zedlabs.wallaperapp2019.ui

import android.app.WallpaperManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.activity_image_details.*
import kotlinx.android.synthetic.main.fab_image_details.*
import android.graphics.Bitmap
import com.bumptech.glide.request.transition.Transition
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.request.target.CustomTarget
import tk.zedlabs.wallaperapp2019.ImageDetailViewModel
import java.io.File
import androidx.core.content.FileProvider
import tk.zedlabs.wallaperapp2019.BuildConfig


class ImageDetails : AppCompatActivity() {

    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabClock: Animation? = null
    private var fabAntiClock: Animation? = null
    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(tk.zedlabs.wallaperapp2019.R.layout.activity_image_details)
        val intent = intent
        val urlLarge = intent.getStringExtra("url_large")
        val urlRegular = intent.getStringExtra("url_regular")
        val id = intent.getStringExtra("id")
        val imageDetailViewModel = ImageDetailViewModel(applicationContext)

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        fabClose = AnimationUtils.loadAnimation(applicationContext, tk.zedlabs.wallaperapp2019.R.anim.fab_close)
        fabOpen = AnimationUtils.loadAnimation(applicationContext, tk.zedlabs.wallaperapp2019.R.anim.fab_open)
        fabClock = AnimationUtils.loadAnimation(applicationContext, tk.zedlabs.wallaperapp2019.R.anim.fab_rotate)
        fabAntiClock = AnimationUtils.loadAnimation(applicationContext,
            tk.zedlabs.wallaperapp2019.R.anim.fab_rotate_anti)

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
            .load(urlRegular)
            .transform(CenterCrop())
            .placeholder(circularProgressDrawable)
            .into(photo_view)

        fab1.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(urlRegular)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageDetailViewModel.downloadImage(resource,id)
                        Toast.makeText(this@ImageDetails,"Download Started",Toast.LENGTH_SHORT).show()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {  }
                })
        }

        val file =  File("${Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
        val uri = FileProvider.getUriForFile(this@ImageDetails,
            BuildConfig.APPLICATION_ID + ".fileprovider", file)

        fab2.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(urlRegular)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageDetailViewModel.downloadImage(resource,id)
                        setWallpaper1(uri)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {  }
                })
        }

    }
    fun setWallpaper1(uri : Uri) {
        try {
            Log.d("imageDetails: ", "Crop and Set: $uri")
            val wallpaperIntent = WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent(uri)
            wallpaperIntent.setDataAndType(uri, "image/*")
            wallpaperIntent.putExtra("mimeType", "image/*")
            startActivityForResult(wallpaperIntent, 13451)
        } catch (e : Exception) {
            e.printStackTrace()
            Log.d("imageDetails", "Chooser: $uri")
            val wallpaperIntent = Intent(Intent.ACTION_ATTACH_DATA)
            wallpaperIntent.setDataAndType(uri, "image/*")
            wallpaperIntent.putExtra("mimeType", "image/*")
            wallpaperIntent.addCategory(Intent.CATEGORY_DEFAULT)
            wallpaperIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            wallpaperIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivity(Intent.createChooser(wallpaperIntent, "Set as wallpaper"))
        }
    }
}
