package com.example.municipaldeputy.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.*
import com.example.municipaldeputy.constants.ACTIVE_VIEW
import com.example.municipaldeputy.constants.PHOTO_VIEW
import com.example.municipaldeputy.constants.WORK_DONE_VIEW
import com.example.municipaldeputy.constants.WORK_PLANE_VIEW
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*


class HouseListActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            dbManager = DBManager(this)
            dbManager.open()
            val houseList=dbManager.fetchHouse(intent.getIntExtra("id", -1))
            with(container) {
                layoutManager = LinearLayoutManager(this@HouseListActivity)
                adapter = HouseAdapter(houseList, applicationContext)
            }
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            PHOTO_VIEW -> {
                val toPhotoGalery=Intent(this@HouseListActivity,PhotoActivity::class.java)
                toPhotoGalery.putExtra("id",item.order.toString())
                startActivity(toPhotoGalery)
            }
            ACTIVE_VIEW -> {
                //тут активные люди
            }
            WORK_PLANE_VIEW -> {
                //тут работы планируемые
            }
            WORK_DONE_VIEW -> {
                //тут работы сделанные
            }
        }
        return super.onContextItemSelected(item)
    }
}