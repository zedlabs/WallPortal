package tk.zedlabs.wallaperapp2019.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_original_resolution.*
import tk.zedlabs.wallaperapp2019.R

class OriginalResolutionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_original_resolution)

        val i = intent
        val url = i.getStringExtra("imageUrl")

        Glide
            .with(this)
            .load(url)
            .fitCenter()
            .into(image_view_original)
    }
}
