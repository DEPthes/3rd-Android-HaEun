package com.example.myapplication.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.NewPhotoEntity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val photoRepositoryImpl = PhotoRepositoryImpl()

    private val _photoState = MutableLiveData<UiState<List<NewPhotoEntity>>>(UiState.Loading)
    val photoState : LiveData<UiState<List<NewPhotoEntity>>> get() = _photoState

    fun getPhotos() {
        _photoState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getPhoto(10, 10, "latest").onSuccess{
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