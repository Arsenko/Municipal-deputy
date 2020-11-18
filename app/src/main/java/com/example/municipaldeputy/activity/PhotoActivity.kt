package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.PhotoAdapter
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
        if (intent.hasExtra("id")) {
            fileService= FileService(applicationContext)
            val list=fileService.getPhotoList(intent.getIntExtra("id",-1))
            container.adapter= PhotoAdapter(list)
        }
    }
}