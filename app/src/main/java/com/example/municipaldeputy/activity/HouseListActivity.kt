package com.example.municipaldeputy.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.HouseAdapter
import com.example.municipaldeputy.constants.ACTIVE_VIEW
import com.example.municipaldeputy.constants.PHOTO_VIEW
import com.example.municipaldeputy.constants.WORK_DONE_VIEW
import com.example.municipaldeputy.constants.WORK_PLANE_VIEW
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_house_list.*


class HouseListActivity : AppCompatActivity() {
    private lateinit var roomViewModel:RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_list)
        init()
    }

    private fun init() {
        if (intent.hasExtra("id")) {
            roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
            val houseAdapter = HouseAdapter()
            roomViewModel.getHouseWithStreetId(intent.getIntExtra("id",
                -1)).observe(this, Observer {
                houseAdapter.list.addAll(it)
                houseAdapter.notifyDataSetChanged()
            })
            with(container){
                layoutManager = LinearLayoutManager(this@HouseListActivity)
                adapter = houseAdapter
            }
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            PHOTO_VIEW -> {
                val toPhotoGalery = Intent(this@HouseListActivity, PhotoActivity::class.java)
                toPhotoGalery.putExtra("id", item.order)
                startActivity(toPhotoGalery)
            }
            ACTIVE_VIEW -> {
                val toActive = Intent(this@HouseListActivity, ActiveActivity::class.java)
                toActive.putExtra("id", item.order)
                startActivity(toActive)
            }
            WORK_PLANE_VIEW -> {
                val toWork = Intent(this@HouseListActivity, WorkActivity::class.java)
                toWork.putExtra("id", item.order)
                toWork.putExtra("done", false)
                startActivity(toWork)
            }
            WORK_DONE_VIEW -> {

                val toWork = Intent(this@HouseListActivity, WorkActivity::class.java)
                toWork.putExtra("id", item.order)
                toWork.putExtra("done", true)
                startActivity(toWork)
            }
        }
        return super.onContextItemSelected(item)
    }
}