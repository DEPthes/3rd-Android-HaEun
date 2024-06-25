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
import com.example.myapplication.ui.GlobalApplication
import com.example.myapplication.ui.detail.DetailFragment
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
        newImagesAdapter = RvNewImagesAdapter().apply {
            setItemClickListener(object : RvNewImagesAdapter.OnItemClickListener{
                override fun onClick(id: String) {
                    DetailFragment(id).apply {
                        setBookmarkClickListener(object : DetailFragment.OnBookmarkClickListener{
                            // 북마크 갱신
                            override fun onBookmarkClick() {
                                mainViewModel.updateBookmark()
                            }
                        })
                        // 얘를 해줘야 fragment 가 보임
                    }.show(requireActivity().supportFragmentManager, "")
                }
            })
        }
        newImagesAdapter.list = data
        binding.newImageRv.adapter = newImagesAdapter
        binding.newImageRv.layoutManager = GridLayoutManager(requireContext(), 2)
        observer()
        mainViewModel.getPhotos(page)

        // bookmark adapter 연결
        bookmarkAdapter = RvBookmarkAdapter().apply {
            setBookmarkItemClickListener(object : RvBookmarkAdapter.OnBookmarkItemClickListener{
                override fun onBookmarkItemClick(id: String) {
                    DetailFragment(id).apply {
                        setBookmarkClickListener(object : DetailFragment.OnBookmarkClickListener{
                            // 북마크 갱신
                            override fun onBookmarkClick() {
                                mainViewModel.updateBookmark()
                            }
                        })
                    }.show(requireActivity().supportFragmentManager, "")
                }
            })
        }
        binding.bookmarkRv.adapter = bookmarkAdapter
        binding.bookmarkRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.bookmarkRv.addItemDecoration(HorizontalSpaceItemDecoration())
        CoroutineScope(Dispatchers.IO).launch {
            val dataList = GlobalApplication.db.getBookmarkDAO().getBookmarkList()
            launch(Dispatchers.Main) {
                bookmarkAdapter.add(dataList)
            }
        }

        setupRecyclerView()

        return binding.root
    }

    // 무한 스크롤 위한 작업
    private fun setupRecyclerView() {
        val layoutManager = binding.newImageRv.layoutManager as GridLayoutManager
        // 스크롤 리스너 설정
        binding.newImageRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                // 스크롤이 끝에 도달하면 다음 페이지 데이터 로드
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == newImagesAdapter.itemCount - 1
                    && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }
            }
        })
    }
    private fun loadNextPage() {
        isLoading = true
        mainViewModel.getPhotos(page)
        page++
    }

    private fun observer() {
        // 최신 이미지 로드
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

        // 북마크 갱신
        mainViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "북마크 갱신 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    // 갱신할 때 북마크가 비어 있는 지 여부
                    if (it.data.isEmpty()) {
                        binding.bookmark.visibility = View.GONE
                        binding.bookmarkRv.visibility = View.GONE
                    }
                    else {
                        binding.bookmark.visibility = View.VISIBLE
                        binding.bookmarkRv.visibility = View.VISIBLE
                    }
                    bookmarkAdapter.add(it.data)
                    Log.d("OBS", "북마크 갱신 성공")
                }
            }
        }
    }
}