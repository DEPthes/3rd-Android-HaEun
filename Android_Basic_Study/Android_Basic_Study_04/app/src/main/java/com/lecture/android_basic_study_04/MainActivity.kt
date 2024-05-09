package com.lecture.android_basic_study_04

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lecture.android_basic_study_04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var data : MutableList<Memo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, MemoActivity::class.java)
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                data.add(Memo(it.data?.getStringExtra("memo").toString()))

                val adapter = MyAdapter()
                adapter.data = data
                Log.d("TAG", "$data")
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                adapter.changeData(data.size - 1)
            }
        }

        binding.button.setOnClickListener {
            launcher.launch(intent)
        }
    }
}