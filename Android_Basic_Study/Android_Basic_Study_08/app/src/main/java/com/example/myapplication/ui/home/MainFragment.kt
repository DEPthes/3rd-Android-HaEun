package com.example.myapplication.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.utils.UiState
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.db.BookmarkDAO
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.ui.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    // binding
    lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var newImagesAdapter: RvNewImagesAdapter
    private lateinit var bookmarkAdapter: RvBookmarkAdapter
    private var page = 1 // 초기 페이지
    private var isLoading = false // API 호출 중 여부

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val data = mutableListOf<NewPhotoEntity>()
        // adapter 연결
        newImagesAdapter = RvNewImagesAdapter()
        newImagesAdapter.list = data
        binding.newImageRv.adapter = newImagesAdapter
        binding.newImageRv.layoutManager = GridLayoutManager(requireContext(), 2)
        observer()
        mainViewModel.getPhotos(page)

        // bookmark adapter 연결
        bookmarkAdapter = RvBookmarkAdapter()
        binding.bookmarkRv.adapter = bookmarkAdapter
        binding.bookmarkRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.bookmarkRv.addItemDecoration(HorizontalSpaceItemDecoration())
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = GlobalApplication.db.getBookmarkDAO().getBookmarkList()
            launch(Dispatchers.Main) {
                bookmarkAdapter.add(dataList)
                if (dataList.isEmpty()) {
                    binding.bookmark.visibility = View.GONE
                    binding.bookmarkRv.visibility = View.GONE
                }
            }
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = binding.newImageRv.layoutManager as GridLayoutManager
        // 스크롤 리스너 설정
        binding.newImageRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                Log.d("isLoading", "$isLoading")
                Log.d("Page", "$page")

                // 스크롤이 끝에 도달하면 다음 페이지 데이터 로드
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == newImagesAdapter.itemCount - 1
                    && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }

                Log.d("isLoading", "$isLoading")
                Log.d("Page", "$page")
            }
        })
    }

    private fun loadNextPage() {
        isLoading = true
        mainViewModel.getPhotos(page)
        page++
    }

    private fun observer() {
        mainViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "사진 로딩 실패")
                    isLoading = false
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    newImagesAdapter.setData(it.data)
                    Log.d("OBS", "성공")
                    isLoading = false
                }
            }
        }
    }
}