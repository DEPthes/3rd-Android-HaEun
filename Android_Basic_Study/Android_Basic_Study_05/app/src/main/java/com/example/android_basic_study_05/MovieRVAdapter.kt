package com.example.android_basic_study_05

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_05.databinding.ItemMovieBinding

class MovieRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movieList = listOf<DailyBoxOfficeList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder) {
            holder.bind(movieList[position])
        }
    }

    fun setData(list: List<DailyBoxOfficeList>) {
        movieList = list
    }

    inner class MovieHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyBoxOfficeList) {
//            binding.tvMovieRank.text = itemView.context.getString(R.string.)
//            binding.tvMovieTitle.text = itemView.context.getString(R.string.text_movie_name, item.movieNm)
//            binding.tvMovieOpen.text = itemView.context.getString(R.string.text_movie_opendate, item.openDt)
//            binding.tvMovieAud.text = itemView.context.getString(R.string.text_movie_aud, item.audiAcc)
        }
    }

    fun addItem(item: DailyBoxOfficeList) {
        val updatedList = ArrayList(movieList)
        updatedList.add(item)
        movieList = updatedList
        notifyItemInserted(movieList.size - 1)
    }

}
