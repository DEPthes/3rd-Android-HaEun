package com.example.myapplication.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.entity.DetailPhotoEntity
import com.example.myapplication.repository.PhotoRepositoryImpl
import com.example.myapplication.ui.GlobalApplication
import com.example.myapplication.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPhotoViewModel : ViewModel() {
    private val photoRepositoryImpl = PhotoRepositoryImpl()

    private val _photoState = MutableLiveData<UiState<DetailPhotoEntity>>(UiState.Loading)
    val photoState : LiveData<UiState<DetailPhotoEntity>> get() = _photoState

    private lateinit var bookmarkUrl : String
    lateinit var downloadLink : String
    lateinit var userName : String
    lateinit var des : String
    lateinit var tags : List<String>

    fun getDetailPhotos(id: String) {
        _photoState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getPhotoDetail(id).onSuccess{
                    _photoState.value = UiState.Success(it)
                    bookmarkUrl = it.thumb
                    downloadLink = it.downloads
                    userName = it.username
                    des = it.description
                    tags = it.tags
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

    private var _bookmarkState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val bookmarkState get() = _bookmarkState
    fun addBookmark(id: String) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                GlobalApplication.db.getBookmarkDAO().addBookmark(BookmarkEntity(id, bookmarkUrl))
                launch(Dispatchers.Main) {
                    _bookmarkState.value = UiState.Success(true)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }
    fun deleteBookmark(id: String) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                GlobalApplication.db.getBookmarkDAO().deleteBookmark(id)
                launch(Dispatchers.Main) {
                    _bookmarkState.value = UiState.Success(true)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }
    private var _checkBookmarkState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val checkBookmarkState get() = _checkBookmarkState
    fun checkBookmark(id: String) {
        _checkBookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (GlobalApplication.db.getBookmarkDAO().checkBookmark(id) == 0) {
                    launch(Dispatchers.Main) {
                        _checkBookmarkState.value = UiState.Success(false)
                    }
                }
                else launch(Dispatchers.Main) {
                    _checkBookmarkState.value = UiState.Success(true)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _checkBookmarkState.value = UiState.Failure(e.message)
            }
        }
    }
}