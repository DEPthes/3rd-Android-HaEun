package com.example.myapplication.ui.card

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCardBinding
import com.example.myapplication.utils.UiState
import kotlin.math.abs

class CardFragment : Fragment() {
    lateinit var binding : FragmentCardBinding
    private val randomViewModel: RandomPhotoViewModel by viewModels()
    private lateinit var randomImagesAdapter: RvRandomImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        // 무한 이미지 로드
        randomImagesAdapter = RvRandomImagesAdapter().apply {
            setItemClickListener(object : RvRandomImagesAdapter.OnItemClickListener{
                override fun onClick(position: Int) {
                    // 추가로 하나씩 더 불러옴
                    binding.viewPager.currentItem = position + 1
                }
            })
        }
        randomImagesAdapter.list = mutableListOf()
        binding.viewPager.adapter = randomImagesAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPager.offscreenPageLimit = 4
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        binding.viewPager.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            // Paging 완료 되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                randomViewModel.getRandomPhotos(1)
            }
        })

        observer()
        randomViewModel.getRandomPhotos(4)

        return binding.root
    }

    private fun observer() {
        randomViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "사진 로딩 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    randomImagesAdapter.add(it.data)
                    Log.d("OBS", "성공")
                }
            }
        }
    }
}