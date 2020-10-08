package tk.zedlabs.wallportal.ui.activity

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
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
import tk.zedlabs.wallportal.repository.BookmarkImage
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.util.makeFadeTransition
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
        when (this.isConnectedToNetwork()) {
            true -> setUpInitialImage(urlFull ?: "")
            false -> shortToast("No Connection")
        }

        imageDetailViewModel.checkIsBookmark(urlRegular ?: "")

        imageDetailViewModel.isBookmark.observe(this, Observer { isBookmark ->
            bookmark_button_1.text =
                if (isBookmark) getString(R.string.remove_from_bookmarks) else getString(R.string.add_to_bookmark)
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
            bookmark_button_1.visibility = View.INVISIBLE

            scrollView1.makeFadeTransition(700)
            bookmark_button_1.visibility = View.VISIBLE
            bookmark_button_1.text = getString(R.string.remove_from_bookmarks)

            CoroutineScope(Dispatchers.IO).launch {
                val idList = bookMarkViewModel.getIdList()
                for (id1 in idList) {
                    if (id == id1) {
                        unique = false;
                        var s1 = getString(R.string.image_already_bookmarked)
                        if (activity == "BookmarkActivity") s1 = getString(R.string.remove_from_bookmarks_qm)

                         Snackbar.make(myCoordinatorLayout, s1, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.remove_string),RemoveListener(BookmarkImage(id, urlFull, urlRegular)))
                            .setActionTextColor(ContextCompat.getColor(this@DetailActivity, R.color.snackBarAction))
                            .show()
                        break
                    }
                }
                if (unique) {
                    bookMarkViewModel.insertBookMarkImage(BookmarkImage(id, urlFull, urlRegular))
                    withContext(Dispatchers.Main) { shortToast("Added to Bookmarks!") }
                }
            }
        }

        highRes_button_1.setOnClickListener {
            val intent1 = Intent(this, OriginalResolutionActivity::class.java)
            intent1.putExtra("imageUrl", urlFull)
            startActivity(intent1)
        }

        CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
            setupDetails(imageDetailViewModel.getImageDetails(id).body()?.imageDetails)
        }
    }

    private fun setupDetails(imageDetails: ImageDetails?) {

        nsw.makeFadeTransition(400)
        image_details_tech_card.visibility = View.VISIBLE
        uploader_tv.text = imageDetails?.uploader?.username
        resolution_tv.text = imageDetails?.resolution
        views_tv.text = imageDetails?.views.toString()
        categories_tv.text = imageDetails?.category
    }

    private fun setUpInitialImage(urlRegular: String) {
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
        try {
            val wallpaperIntent = WallpaperManager
                .getInstance(this)
                .getCropAndSetWallpaperIntent(uri)
                .setDataAndType(uri, "image/*")
                .putExtra("mimeType", "image/*")

            startActivityForResult(wallpaperIntent, 13451)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class RemoveListener(private val bm: BookmarkImage) :
        View.OnClickListener {
        override fun onClick(v: View) {
            CoroutineScope(Dispatchers.IO).launch {
                bookMarkViewModel.delete(bm)
                withContext(Dispatchers.Main) {
                    shortToast("Removed from Bookmarks")
                    this@DetailActivity.onBackPressed()
                }
            }
        }
    }
}
