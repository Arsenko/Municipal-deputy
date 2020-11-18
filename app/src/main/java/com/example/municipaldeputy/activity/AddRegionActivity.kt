package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.HouseAdapter
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.item_add_region.view.*

class AddRegionActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_region)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        with(container) {
            dbManager.insertRegion(region_name_edt.text.toString())
        }

    }
}