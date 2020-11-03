package tk.zedlabs.wallportal.util

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zedlabs.pastelplaceholder.Pastel
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.util.MainAdapter.MyViewHolder
import tk.zedlabs.wallportal.models.WallHavenResponse

class MainAdapter(private val cl: WallpaperClickListener) :
    PagingDataAdapter<WallHavenResponse, MyViewHolder>(diffCallback) {

    class MyViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: WallHavenResponse, clickListener: (WallHavenResponse) -> Unit) {
            itemView.setOnClickListener { clickListener(data) }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<WallHavenResponse>() {
            override fun areItemsTheSame(
                oldItem: WallHavenResponse,
                newItem: WallHavenResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WallHavenResponse,
                newItem: WallHavenResponse
            ): Boolean =
                oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as ImageView
        return MyViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = getItem(position)

        holder.bind(getItem(position)!!, cl.clickListener)

        Glide.with(holder.itemView.context)
            .load(post?.thumbs?.small)
            .placeholder(Pastel().getColorLight())
            .transition(DrawableTransitionOptions.withCrossFade(600))
            .into(holder.itemView.imageViewItem)
    }

}

data class WallpaperClickListener(val clickListener: (wallpaper: WallHavenResponse) -> Unit)

