package com.lecture.android_basic_study_04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lecture.android_basic_study_04.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMemoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val returnIntent = Intent()

        binding.btnIntent.setOnClickListener {
            returnIntent.putExtra("memo", binding.editText.text.toString())
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}