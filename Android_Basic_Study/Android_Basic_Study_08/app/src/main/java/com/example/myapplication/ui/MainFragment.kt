package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.RvNewImagesAdapter
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.utils.photos.MainViewModel
import com.example.myapplication.utils.UiState
import android.util.Log

class MainFragment : Fragment() {
    // binding
    lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var newImagesAdapter: RvNewImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val data = mutableListOf<NewPhotoEntity>()
        Log.d("BIND", "$data")

        newImagesAdapter = RvNewImagesAdapter()
        newImagesAdapter.list = data
        binding.newImageRv.adapter = newImagesAdapter
        binding.newImageRv.layoutManager = GridLayoutManager(requireContext(), 2)

        observer()
        mainViewModel.getPhotos()

        return binding.root
    }

    private fun observer() {
        mainViewModel.photoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("OBS", "사진 로딩 실패")
                }
                is UiState.Loading -> {}
                is UiState.Success -> {
                    newImagesAdapter.setData(it.data)
                    Log.d("OBS", "성공")
                }
            }
        }
    }
}