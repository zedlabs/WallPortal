package tk.zedlabs.wallportal.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_original_resolution.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.util.shortToast

@AndroidEntryPoint
class OriginalResolutionFragment : Fragment(R.layout.activity_original_resolution) {

    private val navArgs: OriginalResolutionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Glide
            .with(this)
            .asBitmap()
            .load(navArgs.urlFull)//.load(intent.getStringExtra("imageUrl"))
            .fitCenter()
            .listener(object : RequestListener<Bitmap?> {

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap?>?,
                                             dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
                    if (resource != null) {
                        textViewLoading.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        or_res_cl.setBackgroundColor(
                            Palette.from(resource).generate().getDarkVibrantColor(
                                ContextCompat.getColor(context!!, R.color.grey)
                            )
                        )
                    }
                    return false
                }

                override fun onLoadFailed(e: GlideException?,model: Any?,target: Target<Bitmap?>?,isFirstResource: Boolean): Boolean {
                    requireContext().shortToast(e?.message ?: "")
                    textViewLoading.text = getString(R.string.failed_to_load)
                    return false
                }
            })
            .into(image_view_original)
    }
}
