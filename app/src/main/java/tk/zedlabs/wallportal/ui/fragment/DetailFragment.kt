package tk.zedlabs.wallportal.ui.fragment

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.databinding.ActivityImageDetailsBinding
import tk.zedlabs.wallportal.models.ImageDetails
import tk.zedlabs.wallportal.persistence.BookmarkImage
import tk.zedlabs.wallportal.util.*
import tk.zedlabs.wallportal.viewmodel.BookmarkViewModel
import tk.zedlabs.wallportal.viewmodel.ImageDetailViewModel

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

        val item = args.listItem
        val uri = requireContext().getUriForId(item.imageName)

        when (requireContext().isConnectedToNetwork()) {
            true -> setUpInitialImage(item.imageUrlFull ?: "")
            false -> requireContext().shortToast("No Connection")
        }

        imageDetailViewModel.checkIsBookmark(item.imageUrlRegular ?: "")

        imageDetailViewModel.isBookmark.observe(requireActivity()) { isBookmark ->
            binding.bookmarkButton.text =
                if (isBookmark) getString(R.string.remove_from_bookmarks) else getString(R.string.add_to_bookmark)
        }

        binding.apply {
            downloadButton.setOnClickListener { download(item) }
            setWallpaperButton.setOnClickListener { setWallpaper(item, uri) }
            bookmarkButton.setOnClickListener { setBookmark(item) }
            originalResolutionButton.setOnClickListener { navigateOriginalRes(item) }
        }

        /** Initial details setup **/
        lifecycleScope.launch {
            val details = async {
                imageDetailViewModel.getImageDetails(item.imageName)
            }
            setupDetails(details.await())
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
        val cpd = CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 10f
            centerRadius = 50f
            backgroundColor = R.color.aquamarine
            start()
        }

        Glide.with(this)
            .load(urlRegular)
            .transform(FitCenter())
            .placeholder(cpd)
            .into(binding.photoView1)

    }

    private fun setWallpaper(item: BookmarkImage, uri: Uri) {

        binding.progressDialog.progressLayout.visibility = View.VISIBLE
        Glide.with(this)
            .asBitmap()
            .load(item.imageUrlFull)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.progressDialog.progressLayout.visibility = View.GONE
                    CoroutineScope(Dispatchers.IO).launch {
                        imageDetailViewModel.downloadImage(resource, item.imageName)
                        withContext(Dispatchers.Main) {
                            startWallpaperIntent(uri)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun startWallpaperIntent(uri: Uri) {
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

    private fun download(item: BookmarkImage) {
        Glide.with(this)
            .asBitmap()
            .load(item.imageUrlFull)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    imageDetailViewModel.downloadImage(resource, item.imageName)
                    requireContext().shortToast("Download Started")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    requireContext().shortToast("Downloaded!")
                }
            })
    }

    private fun setBookmark(item: BookmarkImage) {
        binding.apply {
            bookmarkButton.visibility = View.INVISIBLE
            scrollView1.makeFadeTransition(700)
            bookmarkButton.apply {
                visibility = View.VISIBLE
                text = getString(R.string.remove_from_bookmarks)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            if (bookMarkViewModel.getIdList().contains(item.imageName)) {
                requireContext().showSnackbar(
                    binding.myCoordinatorLayout,
                    RemoveListener(args.listItem)
                )
            } else {
                bookMarkViewModel.insertBookMarkImage(args.listItem)
                requireContext().shortToast(getString(R.string.added_success_bookmark))
            }
        }
    }

    private fun navigateOriginalRes(item: BookmarkImage) {
        findNavController().navigate(
            DetailFragmentDirections.actionDetailsToOriginalRes(
                item.imageUrlFull ?: ""
            )
        )
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
