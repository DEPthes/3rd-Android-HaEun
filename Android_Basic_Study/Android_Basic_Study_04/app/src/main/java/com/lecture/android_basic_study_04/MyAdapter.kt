package com.lecture.android_basic_study_04

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lecture.android_basic_study_04.databinding.ItemRecyclerBinding

class MyAdapter() :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var data = mutableListOf<Memo>()

    inner class ViewHolder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.textView.text = memo.memo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memo = data.get(position)
        holder.bind(memo)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeData(position: Int) {
        notifyItemChanged(position)
        Log.d("Tag", "success")
    }
}