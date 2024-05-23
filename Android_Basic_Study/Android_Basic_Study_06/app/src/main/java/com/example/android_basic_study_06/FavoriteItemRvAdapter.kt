package com.example.android_basic_study_06

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_06.databinding.ItemFavoriteListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var favoriteItemList = mutableListOf<FavoriteItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavoriteListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteItemHolder) {
            holder.bind(favoriteItemList[position])
        }
    }

    fun setData(list: List<FavoriteItem>) {
        favoriteItemList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class FavoriteItemHolder(val binding: ItemFavoriteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context // 컨텍스트 가져오기

        fun bind(item: FavoriteItem) {
            Glide.with(binding.root.context)
                .load(item.thumbnail)
                .override(60, 60)
                .into(binding.ivFavoriteThumbnail)

            binding.tvFavoriteItemName.text = item.title
            binding.tvFavoriteItemPrice.text = item.price.toString()

            binding.btnFavoriteDelete.setOnClickListener {
                deleteFavorites(item.id!!, context, adapterPosition)
            }
        }
    }

    private fun deleteFavorites(id: Int, context: Context, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).getFavoriteDAO()
                .deleteItem(id)
        }
        favoriteItemList.removeAt(position)
        Toast.makeText(context, "removed from favorite list", Toast.LENGTH_SHORT).show()
        notifyItemRemoved(position)
    }
}
class FavoriteItemViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UiState<List<FavoriteItem>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<FavoriteItem>>> get() = _uiState

    fun fetchData(context: Context) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val favoriteList =
                AppDatabase.getInstance(context).getFavoriteDAO().getFavoriteItemList()
            if (favoriteList.isEmpty()) {
                launch(Dispatchers.Main) { _uiState.value = UiState.Failure("데이터 불러오기 실패") }
            } else {
                launch(Dispatchers.Main) {_uiState.value = UiState.Success(favoriteList) }
            }
        }
    }
}