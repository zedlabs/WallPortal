package tk.zedlabs.wallportal.ui.activity

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_image_details.*
import kotlinx.android.synthetic.main.progress_saw.*
import kotlinx.coroutines.*
import tk.zedlabs.wallportal.BuildConfig
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.repository.BookmarkDatabase
import tk.zedlabs.wallportal.repository.BookmarkImage
import tk.zedlabs.wallportal.util.shortToast
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel
import tk.zedlabs.wallportal.viewmodel.ImageDetailViewModel
import java.io.File

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    val imageDetailViewModel: ImageDetailViewModel by viewModels()
    val bookMarkViewModel: BookmarkViewModel by viewModels()

    private val args: DetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        val urlFull = args.listItem.imageUrlFull
        val urlRegular = args.listItem.imageUrlRegular
        val id = args.listItem.imageName
        val activity = args.sender
        val uri = FileProvider.getUriForFile(
            this@DetailActivity, BuildConfig.APPLICATION_ID + ".fileprovider",
            File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
        )

        setUpInitialImage(urlFull ?: "")

        imageDetailViewModel.checkIsBookmark(urlRegular ?: "")

        imageDetailViewModel.isBookmark.observe(this, Observer { isBookmark ->
            bookmark_button_1.text = if (isBookmark) getString(R.string.remove_from_bookmarks) else getString(R.string.add_to_bookmark)
        })

        download_button_1.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageDetailViewModel.downloadImage(resource, id)
                        shortToast("Download Started")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        shortToast("Downloaded!")
                    }
                })
        }

        saw_button_1.setOnClickListener {
            progressLayout.visibility = View.VISIBLE
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        progressLayout.visibility = View.GONE
                        CoroutineScope(Dispatchers.IO).launch {
                            withContext(Dispatchers.IO) {
                                imageDetailViewModel.downloadImage(resource, id)
                            }
                            withContext(Dispatchers.Default) {
                                setWallpaper1(uri)
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }

        bookmark_button_1.setOnClickListener {
            var unique = true
            //todo move coroutines to viewModel
            //todo add remove bookmark logic to back-up and refresh list.
            CoroutineScope(Dispatchers.IO).launch {
                val idList = bookMarkViewModel.getIdList()
                for (id1 in idList) {
                    if (id == id1) {
                        unique = false;
                        var s1 = getString(R.string.image_already_bookmarked)
                        if (activity == "BookmarkActivity") {
                            s1 = getString(R.string.remove_from_bookmarks_qm)
                        }
                        val mySnackbar = Snackbar.make(myCoordinatorLayout, s1, Snackbar.LENGTH_LONG)
                        mySnackbar.setAction(
                            getString(R.string.remove_string),
                            RemoveListener(BookmarkImage(id, urlFull, urlRegular))
                        )
                        mySnackbar.setActionTextColor(
                            ContextCompat.getColor(this@DetailActivity, R.color.snackBarAction)
                        )
                        mySnackbar.show()
                        break
                    }
                }
                if (unique) {
                    bookMarkViewModel.insertBookMarkImage(BookmarkImage(id, urlFull, urlRegular))
                    withContext(Dispatchers.Main) {
                        shortToast("Added to Bookmarks!")
                    }
                    runOnUiThread { this@DetailActivity.recreate() }
                }
            }
        }

        highRes_button_1.setOnClickListener {
            val intent1 = Intent(this, OriginalResolutionActivity::class.java)
            intent1.putExtra("imageUrl", urlFull)
            startActivity(intent1)
        }

        GlobalScope.launch(Dispatchers.Main) {
           setupDetails(imageDetailViewModel.getImageDetails(id!!).body()?.imageDetails)
        }
    }

    private fun setupDetails(imageDetails: ImageDetails?) {
        uploader_tv.text = imageDetails!!.uploader!!.username
        resolution_tv.text = imageDetails.resolution
        views_tv.text = imageDetails.views.toString()
        categories_tv.text = imageDetails.category
    }

    private fun setUpInitialImage(urlRegular: String) {
        //todo setup color shaders instead of loaders
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(urlRegular)
            .transform(FitCenter())
            .placeholder(circularProgressDrawable)
            .into(photo_view_1)

    }

    private fun setWallpaper1(uri: Uri) {
        //todo cleanup
        try {
            Log.d("Main2Activity: ", "Crop and Set: $uri")
            val wallpaperIntent =
                WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent(uri)
            wallpaperIntent.setDataAndType(uri, "image/*")
            wallpaperIntent.putExtra("mimeType", "image/*")
            startActivityForResult(wallpaperIntent, 13451)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("Main2Activity", "Chooser: $uri")
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

    inner class RemoveListener(private val bm: BookmarkImage) :
        View.OnClickListener {
        override fun onClick(v: View) {
            CoroutineScope(Dispatchers.IO).launch {
                bookMarkViewModel.delete(bm)
                withContext(Dispatchers.Main) {
                    shortToast("Removed from Bookmarks")
                }
            }
            this@DetailActivity.recreate()
        }
    }
}
