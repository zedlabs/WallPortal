package tk.zedlabs.wallportal.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LoadImage(url: String) {
    GlideImage(
        imageModel = url,
        // Crop, Fit, Inside, FillHeight, FillWidth, None
        //contentScale = ContentScale.FillHeight,
        loading = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        requestBuilder = Glide
            .with(LocalView.current)
            .asBitmap()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
            .thumbnail(0.1f)
            .transition(BitmapTransitionOptions.withCrossFade()),
        modifier = Modifier.fillMaxHeight(),
        // shows an image with a circular revealed animation.
        //circularRevealedEnabled = true,
        // shows a placeholder ImageBitmap when loading.
        //placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
        // shows an error ImageBitmap when the request failed.
        //error = ImageBitmap.imageResource(R.drawable.error)
    )

}