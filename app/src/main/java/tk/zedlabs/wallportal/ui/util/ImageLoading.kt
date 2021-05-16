package tk.zedlabs.wallportal.ui.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun LoadImage(url: String) {
    GlideImage(
        imageModel = url,
        shimmerParams = ShimmerParams(
            baseColor = Color.DarkGray,
            highlightColor = Color.LightGray,
            durationMillis = 1000,
        ),
        modifier = Modifier.fillMaxHeight(),
    )
}