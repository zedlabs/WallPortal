package tk.zedlabs.wallaperapp2019.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import tk.zedlabs.wallaperapp2019.R
import tk.zedlabs.wallaperapp2019.repository.BookmarkImage

class BookmarkAdapter(private var bookmarkedImages : List<BookmarkImage>, onImageListener: OnImageListener) :
                        RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {


    lateinit var ctx : Context
    private var mOnImageListener: OnImageListener

    init {
        this.mOnImageListener = onImageListener
    }

    class BookmarkViewHolder(imageView: ImageView, onImageListener: OnImageListener) :
                    RecyclerView.ViewHolder(imageView), View.OnClickListener{

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as ImageView
        ctx = parent.context

        return BookmarkViewHolder(
            imageView,
            mOnImageListener
        )
    }

    override fun getItemCount(): Int {
                return bookmarkedImages.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        Glide.with(ctx)
            .load(bookmarkedImages[position].imageUrlRegular!!)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.itemView.imageViewItem)
    }



}