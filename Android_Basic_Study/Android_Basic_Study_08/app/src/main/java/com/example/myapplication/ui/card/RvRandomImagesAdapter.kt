package com.example.myapplication.ui.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.databinding.ItemRandomImagesRvBinding
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.ui.GlobalApplication.Companion.db
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RvRandomImagesAdapter : RecyclerView.Adapter<RvRandomImagesAdapter.RandomViewHolder>() {
    var list = mutableListOf<NewPhotoEntity>()
    inner class RandomViewHolder(private val binding : ItemRandomImagesRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : NewPhotoEntity) {
            Glide.with(binding.cardImg).load(item.thumb).into(binding.cardImg)

            binding.btnBookmark.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db.getBookmarkDAO().addBookmark(BookmarkEntity(item.id, item.thumb))
                }
                itemClickListener.onClick(adapterPosition)
            }
        }
    }
    // onBindViewHolder 보다 먼저 실행
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomViewHolder {
        val binding = ItemRandomImagesRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RandomViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun add(item : List<NewPhotoEntity>) {
        list.addAll(item)
        notifyItemRangeInserted(itemCount, item.size)
    }

    interface OnItemClickListener{
        fun onClick(position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
}