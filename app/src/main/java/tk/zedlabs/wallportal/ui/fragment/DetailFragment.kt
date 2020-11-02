package tk.zedlabs.wallportal.ui.fragment

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import tk.zedlabs.wallportal.BuildConfig
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.databinding.ActivityImageDetailsBinding
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.isConnectedToNetwork
import tk.zedlabs.wallportal.util.makeFadeTransition
import tk.zedlabs.wallportal.util.shortToast
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel
import tk.zedlabs.wallportal.viewmodel.ImageDetailViewModel
import java.io.File

@AndroidEntryPoint
class DetailFragment : Fragment() {

    val imageDetailViewModel: ImageDetailViewModel by viewModels()
    val bookMarkViewModel: BookmarkViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var binding: ActivityImageDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val urlFull = args.listItem.imageUrlFull
        val urlRegular = args.listItem.imageUrlRegular
        val id = args.listItem.imageName

        val uri = FileProvider.getUriForFile(
            requireContext(), BuildConfig.APPLICATION_ID + ".fileprovider",
            File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/WallPortal/$id.jpg")
        )
        when (requireContext().isConnectedToNetwork()) {
            true -> setUpInitialImage(urlFull ?: "")
            false -> requireContext().shortToast("No Connection")
        }

        imageDetailViewModel.checkIsBookmark(urlRegular ?: "")

        imageDetailViewModel.isBookmark.observe(requireActivity(), Observer { isBookmark ->
            binding.bookmarkButton.text =
                if (isBookmark) getString(R.string.remove_from_bookmarks) else getString(R.string.add_to_bookmark)
        })

        binding.downloadButton.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageDetailViewModel.downloadImage(resource, id)
                        requireContext().shortToast("Download Started")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        requireContext().shortToast("Downloaded!")
                    }
                })
        }

        binding.setWallpaperButton.setOnClickListener {

            binding.progressDialog.progressLayout.visibility = View.VISIBLE
            Glide.with(this)
                .asBitmap()
                .load(urlFull)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.progressDialog.progressLayout.visibility = View.GONE
                        CoroutineScope(Dispatchers.IO).launch {
                            imageDetailViewModel.downloadImage(resource, id)
                            withContext(Dispatchers.Main) {
                                setWallpaper1(uri)
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }

        binding.bookmarkButton.setOnClickListener {
            var unique = true
            binding.bookmarkButton.visibility = View.INVISIBLE

            binding.scrollView1.makeFadeTransition(700)

            binding.bookmarkButton.apply {
                visibility = View.VISIBLE
                text = getString(R.string.remove_from_bookmarks)
            }

            CoroutineScope(Dispatchers.Main).launch {
                val idList = bookMarkViewModel.getIdList()
                for (id1 in idList) {
                    if (id == id1) {
                        unique = false

                        Snackbar.make(
                            binding.myCoordinatorLayout,
                            getString(R.string.remove_from_bookmarks_qm),
                            Snackbar.LENGTH_LONG
                        )
                            .setAction(
                                getString(R.string.remove_string), RemoveListener(
                                    BookmarkImage(id, urlFull, urlRegular)
                                )
                            )
                            .setActionTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.snackBarAction
                                )
                            )
                            .show()
                        break
                    }
                }

                if (unique) {
                    bookMarkViewModel.insertBookMarkImage(BookmarkImage(id, urlFull, urlRegular))
                    requireContext().shortToast(getString(R.string.added_success_bookmark))
                }
            }
        }

        binding.originalResolutionButton.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailActivityToOriginalResolutionFragment2(
                urlFull ?: ""
            )
            findNavController().navigate(action)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val details = imageDetailViewModel.getImageDetails(id).body()?.imageDetails

            withContext(Dispatchers.Main) {
               setupDetails(details)
            }
        }
    }

    private fun setupDetails(imageDetails: ImageDetails?) {
            binding.apply {
                nsw.makeFadeTransition(400)
                imageDetailsTechCard.visibility = View.VISIBLE
                uploaderTv.text = imageDetails?.uploader?.username
                resolutionTv.text = imageDetails?.resolution
                viewsTv.text = imageDetails?.views.toString()
                categoriesTv.text = imageDetails?.category
            }
    }

    private fun setUpInitialImage(urlRegular: String) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(this)
            .load(urlRegular)
            .transform(FitCenter())
            .placeholder(circularProgressDrawable)
            .into(binding.photoView1)

    }

    private fun setWallpaper1(uri: Uri) {
        try {
            val wallpaperIntent = WallpaperManager
                .getInstance(requireContext())
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
                    requireContext().shortToast("Removed from Bookmarks")
                    activity?.onBackPressed()
                }
            }
        }
    }

}
