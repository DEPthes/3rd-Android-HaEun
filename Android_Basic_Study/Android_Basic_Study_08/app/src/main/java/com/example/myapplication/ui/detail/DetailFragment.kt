package com.example.myapplication.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.utils.UiState

class DetailFragment(private val id: String) : DialogFragment() {
    lateinit var binding : FragmentDetailBinding
    private val detailViewModel: DetailPhotoViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.btnDetailBookmark.setOnClickListener {
            if (binding.btnDetailBookmark.alpha == 0.3f) {
                detailViewModel.addBookmark(id)
            }
            else detailViewModel.deleteBookmark(id)
        }

        binding.btnDownload.setOnClickListener {

        }

        observer()
        detailViewModel.getDetailPhotos(id)
        detailViewModel.checkBookmark(id)

        return binding.root
    }
    private fun observer() {
        detailViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "사진 로딩 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    Glide.with(binding.detailImg)
                        .load(it.data.thumb)
                        .transform(RoundedCorners(40))
                        .into(binding.detailImg)
                    Log.d("OBS", "성공")
                }
            }
        }
        detailViewModel.checkBookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "북마크 로드 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    if (it.data) binding.btnDetailBookmark.alpha=1f
                    else binding.btnDetailBookmark.alpha=0.3f
                    Log.d("OBS", "북마크 로드 성공")
                }
            }
        }
        detailViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "북마크 로드 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    detailViewModel.checkBookmark(id)
                    Log.d("OBS", "북마크 삭제 성공")
                }
            }
        }
    }
}