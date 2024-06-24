package com.example.myapplication.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.R
import com.example.myapplication.data.db.BookmarkEntity
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.ui.GlobalApplication
import com.example.myapplication.ui.card.RandomPhotoViewModel
import com.example.myapplication.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            CoroutineScope(Dispatchers.IO).launch {
                GlobalApplication.db.getBookmarkDAO().addBookmark(BookmarkEntity(id, ""))
            }
        }

        observer()
        detailViewModel.getDetailPhotos(id)

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
    }
}