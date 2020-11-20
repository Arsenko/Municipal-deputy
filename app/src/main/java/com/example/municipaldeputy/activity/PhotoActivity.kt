package com.example.municipaldeputy.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.PhotoAdapter
import com.example.municipaldeputy.constants.REQUEST_CODE
import com.example.municipaldeputy.service.FileService
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : FragmentActivity() {
    private lateinit var fileService:FileService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        init()
    }

    private fun init() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE
            )
        }//можно не принять, тогда будут только ресурсы
        if (intent.hasExtra("id")) {
            fileService= FileService(applicationContext)
            val list=fileService.getPhotoList(intent.getIntExtra("id",-1))
            container.adapter= PhotoAdapter(list)
        }
    }
}