package com.example.myapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemNewImagesRvBinding
import com.example.myapplication.entity.NewPhotoEntity

class RvNewImagesAdapter : RecyclerView.Adapter<RvNewImagesAdapter.ViewHolder>() {
    var list = listOf<NewPhotoEntity>()
    inner class ViewHolder(private val binding : ItemNewImagesRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : NewPhotoEntity) {
            Log.d("TAG", "testing")
            Glide.with(binding.rvImg).load(item.thumb).into(binding.rvImg)
            binding.rvId.text = item.description
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
        list = item
        notifyItemRangeInserted(itemCount, item.size)
    }
}