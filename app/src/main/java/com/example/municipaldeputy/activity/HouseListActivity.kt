package com.example.municipaldeputy.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.municipaldeputy.R
import com.example.municipaldeputy.activity.add.*
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent:Intent?=null
        when(item.itemId){
            R.id.action_region ->{
                intent= Intent(this, AddRegionActivity::class.java)
            }
            R.id.action_district ->{
                intent= Intent(this, AddDistrictActivity::class.java)
            }
            R.id.action_street ->{
                intent= Intent(this, AddStreetActivity::class.java)
            }
            R.id.action_house ->{
                intent= Intent(this, AddHouseActivity::class.java)
            }
            R.id.action_photo ->{
                intent= Intent(this, AddPhotoActivity::class.java)
            }
            R.id.action_work ->{
                intent= Intent(this, AddWorkActivity::class.java)
            }
            R.id.action_active ->{
                intent= Intent(this, AddActiveActivity::class.java)
            }
        }
        startActivity(intent)
        return true
    }
}