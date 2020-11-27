package com.example.municipaldeputy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.WorkAdapter
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_house_list.*


class WorkActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
            val workAdapter=WorkAdapter()
            val flag=intent.getBooleanExtra("done", false)
            val id=intent.getIntExtra("id", -1)
            if(flag){
                roomViewModel.getDoneDataWithHouseId(id).observe(this,
                    Observer {
                        workAdapter.list.addAll(it)
                        workAdapter.notifyDataSetChanged()
                    })
            }else{
                roomViewModel.getUndoneDataWithHouseId(id).observe(this,
                    Observer {
                        workAdapter.list.addAll(it)
                        workAdapter.notifyDataSetChanged()
                    })
            }
            with(container) {
                layoutManager = LinearLayoutManager(this@WorkActivity)
                adapter = workAdapter
            }
        }

    }
}