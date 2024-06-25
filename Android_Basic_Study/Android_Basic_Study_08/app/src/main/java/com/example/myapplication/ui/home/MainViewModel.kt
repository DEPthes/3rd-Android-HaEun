package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.utils.UiState
import com.example.myapplication.repository.PhotoRepositoryImpl
import com.example.myapplication.ui.GlobalApplication
import kotlinx.coroutines.Dispatchers
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

    private var _bookmarkState = MutableLiveData<UiState<List<BookmarkEntity>>>(UiState.Loading)
    val bookmarkState get() = _bookmarkState

    fun updateBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 북마크 리스트 부르기
                val it = GlobalApplication.db.getBookmarkDAO().getBookmarkList()
                launch(Dispatchers.Main) {
                    bookmarkState.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) { _bookmarkState.value = UiState.Failure(e.message) }
            }
        }
    }
}