package com.lecture.android_basic_study_04

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lecture.android_basic_study_04.databinding.ItemRecyclerBinding

class MyAdapter(private val data: MutableList<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.rcyTxt.text = item
        }
        @SuppressLint("NotifyDataSetChanged")
        fun delete(position: Int) {
            binding.btnDelete.setOnClickListener {
                data.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.delete(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}