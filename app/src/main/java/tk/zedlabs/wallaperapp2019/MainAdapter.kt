package tk.zedlabs.wallaperapp2019

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import tk.zedlabs.wallaperapp2019.MainAdapter.MyViewHolder
import tk.zedlabs.wallaperapp2019.models.UnsplashImageDetails

class MainAdapter(onImageListener: OnImageListener) :
    PagedListAdapter<UnsplashImageDetails, MyViewHolder>(diffCallback) {

    private lateinit var ctx : Context
    private var mOnImageListener: OnImageListener

    init {
        this.mOnImageListener = onImageListener
    }

    class MyViewHolder(imageView: ImageView,onImageListener: OnImageListener) :
        RecyclerView.ViewHolder(imageView),View.OnClickListener {

        var onImageListener : OnImageListener
        init {
            this.onImageListener = onImageListener
            imageView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onImageListener.onImageClick(adapterPosition)
        }
    }

    interface OnImageListener{
        fun onImageClick(position: Int)
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<UnsplashImageDetails>() {
            override fun areItemsTheSame(oldItem: UnsplashImageDetails, newItem: UnsplashImageDetails): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashImageDetails, newItem: UnsplashImageDetails): Boolean =
                oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as ImageView
        ctx = parent.context
        return MyViewHolder(imageView,mOnImageListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = getItem(position)
        Glide.with(ctx)
            .load(post?.urls?.regular)
            .into(holder.itemView.imageViewItem)
    }

}
