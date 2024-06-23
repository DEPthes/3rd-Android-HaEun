package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBookmarkRvBinding

class RvBookmarkAdapter : RecyclerView.Adapter<RvBookmarkAdapter.ViewHolder>() {
    var list = mutableListOf<String>()
    inner class ViewHolder(private val binding : ItemBookmarkRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(img : Int) {
            binding.bookmarkRvImg.imageAlpha = R.drawable.cookie
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
        holder.bind(R.drawable.cookie)
    }

    fun add(item : String) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun update(newList: List<String>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }
}