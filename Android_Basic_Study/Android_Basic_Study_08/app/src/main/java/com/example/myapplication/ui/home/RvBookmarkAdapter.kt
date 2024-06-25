package com.example.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.R
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.databinding.ItemBookmarkRvBinding
import com.example.myapplication.entity.NewPhotoEntity

class RvBookmarkAdapter : RecyclerView.Adapter<RvBookmarkAdapter.ViewHolder>() {
    var list = listOf<BookmarkEntity>()
    inner class ViewHolder(private val binding : ItemBookmarkRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : BookmarkEntity) {
            Glide.with(binding.bookmarkRvImg)
                .load(item.url)
                .transform(RoundedCorners(80))
                .into(binding.bookmarkRvImg)

            itemView.setOnClickListener {
                bookmarkItemClickListener.onBookmarkItemClick(item.id)
            }
        }
    }
    // onBindViewHolder 보다 먼저 실행
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookmarkRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(item : List<BookmarkEntity>) {
        list = item
        notifyDataSetChanged()
    }
    interface OnBookmarkItemClickListener{
        fun onBookmarkItemClick(id: String)
    }

    private lateinit var bookmarkItemClickListener: OnBookmarkItemClickListener

    fun setBookmarkItemClickListener(onBookmarkItemClickListener: OnBookmarkItemClickListener){
        this.bookmarkItemClickListener = onBookmarkItemClickListener
    }
}