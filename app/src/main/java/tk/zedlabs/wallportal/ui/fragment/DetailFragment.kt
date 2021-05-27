package tk.zedlabs.wallportal.ui.fragment

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.ui.util.LoadImage
import tk.zedlabs.wallportal.util.Resource
import tk.zedlabs.wallportal.util.getUriForId
import tk.zedlabs.wallportal.util.shortToast
import android.content.Intent
import androidx.compose.material.icons.sharp.*
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.*
import tk.zedlabs.wallportal.models.WallHavenResponse


@ExperimentalMaterialApi
@AndroidEntryPoint
class DetailFragment : Fragment() {

    val bookMarkViewModel: BookmarkViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                DetailsContentWrapper()
            }
        }
    }

    @Composable
    fun DetailsContentWrapper() {
        val imageDetails = produceState<Resource<WallHavenResponse>>(
            initialValue = Resource.Loading()
        ) {
            value = bookMarkViewModel.getImageDetails(args.id)
        }.value

        when (imageDetails) {
            is Resource.Success -> {
                DetailsContent(imageDetails.data!!)
                bookMarkViewModel.checkBookmark(imageDetails.data.id!!)
            }
            is Resource.Error -> {
                Text(text = imageDetails.message!!, color = Color.Red)
            }
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }

            }
        }
    }

    @Composable
    fun DetailsContent(imageDetails: WallHavenResponse) {
        val isBookmark by bookMarkViewModel.isBookmark.observeAsState()
        BottomSheetScaffold(
            sheetContent = {
                ImageInformationAndOptions(imageDetails = imageDetails)
            },
            sheetBackgroundColor = colorResource(R.color.pastelPrimary).copy(alpha = 0.8f),
            sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            sheetPeekHeight = 90.dp,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.listBackground))
            ) {
                LoadImage(url = imageDetails.path!!)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(45.dp)
                            .background(colorResource(R.color.pastelPrimary).copy(alpha = 0.4f))
                            .clickable { findNavController().navigateUp() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back-button",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(45.dp)
                            .background(colorResource(R.color.pastelPrimary).copy(alpha = 0.4f))
                            .clickable { addBookmark(isBookmark!!, imageDetails) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (isBookmark!!) {
                                true -> Icons.Outlined.Bookmark
                                else -> Icons.Outlined.BookmarkBorder
                            },
                            contentDescription = "bookmark-button",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

            }
        }
    }

    @Composable
    fun ImageInformationAndOptions(
        imageDetails: WallHavenResponse,
    ) {
        //options icons row --downloads --setWallpaper --bookmark --externalLink
        Column(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 20.dp)
        ) {
            Divider(
                modifier = Modifier
                    .width(80.dp)
                    .height(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(3.dp))
                    .alpha(0.3f),
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = Icons.Outlined.FileDownload,
                    contentDescription = "download-image",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { download(imageDetails.path!!, imageDetails.id!!) },
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Sharp.Wallpaper,
                    contentDescription = "set-as-wallpaper",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { setWallpaper(imageDetails) },
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Outlined.OpenInNew,
                    contentDescription = "open-in-browser",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(imageDetails.url)
                            )
                            startActivity(browserIntent)
                        },
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Outlined.Landscape,
                    contentDescription = "original-aspect-ratio",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navigateOriginalRes(imageDetails.path!!) },
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "share-image",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { shareImage(imageDetails.url!!) },
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .align(Alignment.CenterHorizontally)
                    .alpha(0.3f),
                color = Color.LightGray
            )
            // --uploader --resolution --views --category
            RowWithIconAndText(Icons.Sharp.AccountCircle, imageDetails.uploader?.username ?: "")
            RowWithIconAndText(Icons.Sharp.HdrPlus, imageDetails.resolution ?: "")
            RowWithIconAndText(Icons.Sharp.Fingerprint, (imageDetails.views ?: "").toString())
            RowWithIconAndText(Icons.Sharp.Category, imageDetails.category ?: "")
        }
    }

    @Composable
    fun RowWithIconAndText(icon: ImageVector, text: String) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Icon(
                imageVector = icon,
                contentDescription = "profile",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.White)
        }

    }

    private fun addBookmark(isBookmark: Boolean, imageDetails: WallHavenResponse) {
        if (!isBookmark) {
            bookMarkViewModel.setBookmark(imageDetails)
            requireContext().shortToast("Bookmark Added!")
        } else {
            bookMarkViewModel.deleteBookmark(imageDetails.id!!)
            requireContext().shortToast("Bookmark Removed")
        }
    }

    private fun setWallpaper(imageDetails: WallHavenResponse) {
        Glide.with(this)
            .asBitmap()
            .load(imageDetails.path!!)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    CoroutineScope(Dispatchers.IO).launch {
                        bookMarkViewModel.downloadImage(resource, imageDetails.id!!)
                        withContext(Dispatchers.Main) {
                            startWallpaperIntent(requireContext().getUriForId(imageDetails.id))
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

            startActivity(wallpaperIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun download(urlFull: String, id: String) {
        Glide.with(this)
            .asBitmap()
            .load(urlFull)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    bookMarkViewModel.downloadImage(resource, id)
                    requireContext().shortToast("Download Started")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    requireContext().shortToast("Downloaded!")
                }
            })
    }

    private fun navigateOriginalRes(urlFull: String) {
        findNavController().navigate(
            DetailFragmentDirections.detailsToOR(urlFull)
        )
    }

    private fun shareImage(url: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
