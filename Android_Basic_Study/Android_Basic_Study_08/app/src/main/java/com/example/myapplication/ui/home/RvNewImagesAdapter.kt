package com.example.myapplication.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.databinding.ItemNewImagesRvBinding
import com.example.myapplication.entity.NewPhotoEntity

class RvNewImagesAdapter : RecyclerView.Adapter<RvNewImagesAdapter.ViewHolder>() {
    var list = mutableListOf<NewPhotoEntity>()
    inner class ViewHolder(private val binding : ItemNewImagesRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : NewPhotoEntity) {
            Glide.with(binding.photoRvImg)
                .load(item.thumb)
                .transform(RoundedCorners(40))
                .into(binding.photoRvImg)
            binding.rvId.text = item.description

            itemView.setOnClickListener {
                itemClickListener.onClick(item.id)
            }
        }
    }
    // onBindViewHolder 보다 먼저 실행
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewImagesRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(item : List<NewPhotoEntity>) {
        Log.d("setData", "setData")
        list.addAll(item)
        notifyItemRangeInserted(itemCount, item.size)
    }
    interface OnItemClickListener{
        fun onClick(id: String)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
}