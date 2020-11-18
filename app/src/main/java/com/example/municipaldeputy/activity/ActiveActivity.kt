package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.ActiveAdapter
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*

class ActiveActivity: AppCompatActivity() {
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
            val activeList=dbManager.fetchActive(intent.getIntExtra("id", -1))
            with(container) {
                layoutManager = LinearLayoutManager(this@ActiveActivity)
                adapter = ActiveAdapter(activeList)
            }
        }
    }
}