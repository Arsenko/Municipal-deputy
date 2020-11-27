package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.ActiveAdapter
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_house_list.*

class ActiveActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
            val adapter = ActiveAdapter()
            val id=intent.getIntExtra("id", -1)
            roomViewModel.getActiveDataByHouseId(id).observe(this,
                Observer {
                    adapter.list.addAll(it)
                    adapter.notifyDataSetChanged()
                })
            container.layoutManager = LinearLayoutManager(this@ActiveActivity)
            container.adapter = adapter
        }
    }
}
