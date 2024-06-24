package com.example.myapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.DetailPhotoEntity
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.repository.PhotoRepositoryImpl
import com.example.myapplication.utils.UiState
import kotlinx.coroutines.launch

class DetailPhotoViewModel : ViewModel() {
    private val photoRepositoryImpl = PhotoRepositoryImpl()

    private val _photoState = MutableLiveData<UiState<DetailPhotoEntity>>(UiState.Loading)
    val photoState : LiveData<UiState<DetailPhotoEntity>> get() = _photoState

    fun getDetailPhotos(id: String) {
        _photoState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getPhotoDetail(id).onSuccess{
                    _photoState.value = UiState.Success(it)
                }.onFailure {
                    _photoState.value = UiState.Failure(it.message)
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                _photoState.value = UiState.Failure(e.message)
            }
        }
    }
}