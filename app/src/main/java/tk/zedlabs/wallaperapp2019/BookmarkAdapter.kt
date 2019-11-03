package tk.zedlabs.wallaperapp2019

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class BookmarkAdapter(private val bookmarkedImages : List<BookmarkImage>) :
                        RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    lateinit var ctx : Context
    class BookmarkViewHolder(imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as ImageView
        ctx = parent.context
        return BookmarkViewHolder(imageView)
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