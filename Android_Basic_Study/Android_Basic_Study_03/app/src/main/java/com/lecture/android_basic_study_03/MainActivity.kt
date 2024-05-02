package com.lecture.android_basic_study_03

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.lecture.android_basic_study_03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var alertDialog: Builder?
        get() = null
        set(value) = TODO()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = checkNotNull(result.data)
                val imageUri = intent.data

                Glide.with(this)
                    .load(imageUri)
                    .into(binding.ivTest)
            }
        }

        binding.btnLoad.setOnClickListener {
            if(checkGalleryPermission()){
                val intent = Intent().also { intent ->
                    intent.type = "image/"
                    intent.action = Intent.ACTION_GET_CONTENT
                }
                launcher.launch(intent)
            } else {
                requestGalleryPermission()
            }
        }
    }

    private fun checkGalleryPermission(): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestGalleryPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (shouldShowRequestPermissionRationale(permission[0])) {
            showPermissionRationale("설정에서 저장소 권한을 추가해야 합니다.")
        } else {
            ActivityCompat.requestPermissions(this, permission, 100)
        }
    }

    private fun showPermissionRationale(msg: String) {
        alertDialog = Builder(this)
        alertDialog?.setMessage(msg)
        alertDialog?.setPositiveButton("확인") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", this.packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        alertDialog?.setNegativeButton("취소") { _, _ ->
        }

        alertDialog?.show()
    }
}