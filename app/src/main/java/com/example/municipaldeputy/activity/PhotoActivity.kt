package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.PageAdapter
import com.example.municipaldeputy.constants.P_KEY_FILEPATH
import com.example.municipaldeputy.constants.P_KEY_HOUSE_ID
import com.example.municipaldeputy.service.FileService
import com.example.municipaldeputy.sqlite.DBManager
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
            container.adapter= PageAdapter(list)
        }
    }
}