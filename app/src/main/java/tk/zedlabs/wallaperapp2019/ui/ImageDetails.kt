package tk.zedlabs.wallaperapp2019.ui

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_image_details.*
import kotlinx.android.synthetic.main.fab_image_details.*
import kotlinx.android.synthetic.main.progress_saw.*
import kotlinx.coroutines.*
import tk.zedlabs.wallaperapp2019.repository.BookmarkDatabase
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage
import tk.zedlabs.wallaperapp2019.BuildConfig
import tk.zedlabs.wallaperapp2019.R
import tk.zedlabs.wallaperapp2019.viewmodel.ImageDetailViewModel
import tk.zedlabs.wallaperapp2019.R.anim.*
import tk.zedlabs.wallaperapp2019.R.layout
import java.io.File


class ImageDetails : AppCompatActivity() {

    private var fabOpen: Animation? = null
    private var fabClose: Animation? = null
    private var fabClock: Animation? = null
    private var fabAntiClock: Animation? = null
    private var isOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_image_details)
        val intent = intent
        val urlFull = intent.getStringExtra("url_large")
        val urlRegular = intent.getStringExtra("url_regular")
        val id = intent.getStringExtra("id")
        val activity = intent.getStringExtra("Activity")
        val imageDetailViewModel =ImageDetailViewModel(applicationContext)

        val uri=FileProvider.getUriForFile(this@ImageDetails,BuildConfig.APPLICATION_ID +".fileprovider",
            File("${getExternalStoragePublicDirectory(DIRECTORY_PICTURES)}/WallPortal/$id.jpg"))

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        fabClose = loadAnimation(applicationContext,fab_close)
        fabOpen = loadAnimation(applicationContext,fab_open)
        fabClock = loadAnimation(applicationContext,fab_rotate)
        fabAntiClock = loadAnimation(applicationContext,fab_rotate_anti)

        fab?.setOnClickListener {
            if (isOpen) {
               fabClose()
            } else {
                fabOpen()
            }
        }
        Glide.with(this)
            .load(urlRegular)
            .transform(CenterCrop())
            .placeholder(circularProgressDrawable)
            .into(photo_view)

        download_button.setOnClickListener {
            fabClose()
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageDetailViewModel.downloadImage(resource,id)
                        Toast.makeText(this@ImageDetails,"Download Started",Toast.LENGTH_SHORT).show()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {  }
                })
        }
        saw_button.setOnClickListener {
            progressLayout.visibility = View.VISIBLE
            fabClose()
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        progressLayout.visibility = View.GONE
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.IO) {
                                imageDetailViewModel.downloadImage(resource, id)
                            }
                            withContext(Dispatchers.Default){
                                setWallpaper1(uri)
                            }
                        }
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {  }
                })
        }
        bookmark_button.setOnClickListener {
            fabClose()
            val db = Room.databaseBuilder(
                applicationContext,
                BookmarkDatabase::class.java, "bookmark-database"
            ).build()
            var unique = true
            CoroutineScope(Dispatchers.IO).launch {
                val idList = db.bookmarkDao().getId()
                for (id1 in idList) {
                    if (id == id1) {
                        unique = false
                        val mySnackbar = Snackbar.make(
                            myConstraintLayout,
                            "Image already bookmarked",
                            Snackbar.LENGTH_LONG
                        )
                        mySnackbar.show()
                        break
                    }
                }
                if(unique){
                    db.bookmarkDao().insert(BookmarkImage(id, urlFull, urlRegular))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ImageDetails, "Added to Bookmarks!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }


    }
    private fun fabClose(){
        textview_down.visibility = View.INVISIBLE
        textview_saw.visibility = View.INVISIBLE
        textview_highRes.visibility = View.INVISIBLE
        textview_bookmark.visibility = View.INVISIBLE

        textview_down.startAnimation(fabClose)
        textview_saw.startAnimation(fabClose)
        textview_highRes.startAnimation(fabClose)
        textview_bookmark.startAnimation(fabClose)

        saw_button.startAnimation(fabClose)
        download_button.startAnimation(fabClose)
        highRes_button.startAnimation(fabClose)
        bookmark_button.startAnimation(fabClose)

        fab.startAnimation(fabAntiClock)
        saw_button.isClickable = false
        download_button.isClickable = false
        highRes_button.isClickable = false
        bookmark_button.isClickable = false
        isOpen = false
    }
    private fun fabOpen(){
        textview_down.visibility = View.VISIBLE
        textview_saw.visibility = View.VISIBLE
        textview_highRes.visibility = View.VISIBLE
        textview_bookmark.visibility = View.VISIBLE

        textview_down.startAnimation(fabOpen)
        textview_saw.startAnimation(fabOpen)
        textview_highRes.startAnimation(fabOpen)
        textview_bookmark.startAnimation(fabOpen)

        saw_button.startAnimation(fabOpen)
        download_button.startAnimation(fabOpen)
        highRes_button.startAnimation(fabOpen)
        bookmark_button.startAnimation(fabOpen)

        fab.startAnimation(fabClock)
        saw_button.isClickable = true
        download_button.isClickable = true
        highRes_button.isClickable = true
        bookmark_button.isClickable = true
        isOpen = true
    }
    private fun setWallpaper1(uri : Uri) {
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
