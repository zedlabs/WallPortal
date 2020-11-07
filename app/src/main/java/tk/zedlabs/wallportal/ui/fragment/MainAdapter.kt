package tk.zedlabs.wallportal.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zedlabs.pastelplaceholder.Pastel
import tk.zedlabs.wallportal.R
import tk.zedlabs.wallportal.models.WallHavenResponse

class MainAdapter(private val cl: (wallpaper: WallHavenResponse) -> Unit) :
    PagingDataAdapter<WallHavenResponse, MyViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<WallHavenResponse>() {
            override fun areItemsTheSame(old: WallHavenResponse, new: WallHavenResponse) =
                old.id == new.id

            override fun areContentsTheSame(old: WallHavenResponse, new: WallHavenResponse) =
                old.equals(new)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as ImageView
        return MyViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = getItem(position)

        holder.bind(getItem(position)!!, cl)

        Glide.with(holder.itemView.context)
            .load(post?.thumbs?.small)
            .placeholder(Pastel.getColorLight())
            .transition(DrawableTransitionOptions.withCrossFade(600))
            .into(holder.itemView.findViewById(R.id.imageViewItem))
    }

}

class MyViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: WallHavenResponse, clickListener: (WallHavenResponse) -> Unit) {
        itemView.setOnClickListener { clickListener(data) }
    }
}


