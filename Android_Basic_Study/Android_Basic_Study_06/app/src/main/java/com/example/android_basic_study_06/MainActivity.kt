package com.example.android_basic_study_06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_basic_study_06.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// MainActivity.kt
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val itemViewModel: ItemViewModel by viewModels()
    private lateinit var itemRvAdapter: ItemRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observer()


        binding.btnSearchItem.setOnClickListener {
            val searchItem = binding.etSearchItem.text.toString()

            // recyclerview adapter
            itemRvAdapter = ItemRvAdapter()

            // set up recyclerview
            binding.rvItemList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = itemRvAdapter
            }
            // set data
            itemViewModel.fetchData(searchItem)
        }

        binding.btnFavoriteList.setOnClickListener {
            val intent = Intent(this, FavoriteListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observer() {
        itemViewModel.uiState.observe(this) {
            when (it) {
                is UiState.Failure -> {
                    Log.d("TAG", "fail")
                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {}
                is UiState.Success -> {
                    Log.d("TAG", "success")
                    itemRvAdapter.setData(it.data)
                }

                else -> {}
            }
        }
    }
}