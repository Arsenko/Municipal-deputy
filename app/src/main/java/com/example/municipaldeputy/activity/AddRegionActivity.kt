package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_add_region.*

class AddRegionActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_region)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        add_btn.setOnClickListener {
            dbManager.insertRegion(region_name_edt.text.toString())
        }

    }
}