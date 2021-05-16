package tk.zedlabs.wallportal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OriginalResolutionFragment : Fragment() {

    private val navArgs: OriginalResolutionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ImageDetails(navArgs.urlFull)
            }
        }
    }

    @Composable
    fun ImageDetails(url: String) {
        GlideImage(
            imageModel = url,
            contentScale = ContentScale.Fit,
            shimmerParams = ShimmerParams(
                baseColor = Color.DarkGray,
                highlightColor = Color.LightGray,
                durationMillis = 1000,
            ),
            modifier = Modifier.fillMaxHeight(),
            failure = {
                Text(text = "Failed To Load Image")
            }
        )
    }

}
