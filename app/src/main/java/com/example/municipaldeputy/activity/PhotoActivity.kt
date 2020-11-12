package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager

class PhotoActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            dbManager = DBManager(this)
            dbManager.open()
        }
    }
}
