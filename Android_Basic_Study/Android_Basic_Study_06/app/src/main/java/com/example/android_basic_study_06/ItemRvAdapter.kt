package com.example.android_basic_study_06

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_06.databinding.ItemRvBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = mutableListOf<ItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            holder.bind(itemList[position])
        }
    }

    fun setData(list: List<ItemEntity>) {
        itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemHolder(val binding: ItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context // 컨텍스트 가져오기
        fun bind(item: ItemEntity) {
            Glide.with(context)
                .load(item.thumbnail)
                .override(60, 60)
                .into(binding.ivThumbnail)
            binding.tvItemName2.text = item.title
            binding.tvItemPrice.text = item.price.toString()

            binding.btnFavoriteAdd.setOnClickListener {
                addToFavorites(item, context) // 아이템 찜 목록에 추가
            }
        }
    }

    private fun addToFavorites(item: ItemEntity, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).getFavoriteDAO()
                .insertItem(FavoriteItem(null, item.price, item.thumbnail, item.title))
        }
        Toast.makeText(context, "add to your favorite", Toast.LENGTH_SHORT).show()
    }
}

class ItemViewModel : ViewModel() {
    private val itemRepositoryImpl = ItemRepositoryImpl()

    private val _uiState = MutableLiveData<UiState<List<ItemEntity>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<ItemEntity>>> get() = _uiState

    fun fetchData(searchItem: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            itemRepositoryImpl.getItem(searchItem)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }
}