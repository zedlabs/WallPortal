package tk.zedlabs.wallportal.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.databinding.ActivityOriginalResolutionBinding
import tk.zedlabs.wallportal.util.shortToast

@AndroidEntryPoint
class OriginalResolutionFragment : Fragment() {

    private val navArgs: OriginalResolutionFragmentArgs by navArgs()

    private var _binding: ActivityOriginalResolutionBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityOriginalResolutionBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Glide
            .with(this)
            .asBitmap()
            .load(navArgs.urlFull)
            .fitCenter()
            .listener(object : RequestListener<Bitmap?> {

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap?>?,
                                             dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
                    if (resource != null) {
                        binding.textViewLoading.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.orResCl.setBackgroundColor(
                            Palette.from(resource).generate().getDarkVibrantColor(
                                ContextCompat.getColor(context!!, R.color.grey)
                            )
                        )
                    }
                    return false
                }

                override fun onLoadFailed(e: GlideException?,model: Any?,target: Target<Bitmap?>?,isFirstResource: Boolean): Boolean {
                    requireContext().shortToast(e?.message ?: "")
                    binding.textViewLoading.text = getString(R.string.failed_to_load)
                    return false
                }
            })
            .into(binding.imageViewOriginal)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
