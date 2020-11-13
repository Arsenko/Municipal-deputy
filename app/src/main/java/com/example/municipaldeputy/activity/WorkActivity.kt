package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.*
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*


class WorkActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            dbManager = DBManager(this)
            dbManager.open()
            val houseList = dbManager.fetchWork(
                intent.getIntExtra("id", -1),
                intent.getBooleanExtra("done", false)
            )
            with(container) {
                layoutManager = LinearLayoutManager(this@WorkActivity)
                adapter = WorkAdapter(houseList)
            }
        }

    }
}