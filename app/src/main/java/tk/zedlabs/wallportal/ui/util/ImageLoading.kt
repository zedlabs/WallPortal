package tk.zedlabs.wallportal.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LoadImage(url: String) {
    GlideImage(
        imageModel = url,
        loading = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        requestBuilder = Glide
            .with(LocalView.current)
            .asBitmap()
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()),
        modifier = Modifier.fillMaxHeight(),
    )


}