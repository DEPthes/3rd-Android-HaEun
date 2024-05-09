package com.lecture.android_basic_study_04

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lecture.android_basic_study_04.databinding.ActivityMainBinding
import com.lecture.android_basic_study_04.databinding.ItemRecyclerBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var data = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MyAdapter(data)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val intent = Intent(this, MemoActivity::class.java)
        val launcher
        = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                data.add(it.data?.getStringExtra("memo").toString())
                adapter.notifyDataSetChanged()
            }
        }

        binding.button.setOnClickListener {
            launcher.launch(intent)
        }
    }
}