package com.example.myapplication.ui.card

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemRandomImagesRvBinding
import com.example.myapplication.entity.NewPhotoEntity

class RvRandomImagesAdapter : RecyclerView.Adapter<RvRandomImagesAdapter.RandomViewHolder>() {
    var list = mutableListOf<NewPhotoEntity>()
    inner class RandomViewHolder(private val binding : ItemRandomImagesRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : NewPhotoEntity) {
            Log.d("BIND2", "testing")
            Glide.with(binding.cardImg).load(item.thumb).into(binding.cardImg)
//            binding.rvId.text = item.description
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
}