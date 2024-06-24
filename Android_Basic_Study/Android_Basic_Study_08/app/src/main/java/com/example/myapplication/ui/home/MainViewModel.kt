package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.utils.UiState
import com.example.myapplication.repository.PhotoRepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val photoRepositoryImpl = PhotoRepositoryImpl()

    private val _photoState = MutableLiveData<UiState<List<NewPhotoEntity>>>(UiState.Loading)
    val photoState : LiveData<UiState<List<NewPhotoEntity>>> get() = _photoState

    fun getPhotos(page: Int) {
        _photoState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getPhoto(page, 10, "latest").onSuccess{
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