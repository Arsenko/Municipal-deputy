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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent:Intent?=null
        when(item.itemId){
            R.id.action_region ->{
                intent= Intent(this,AddRegionActivity::class.java)
            }
            R.id.action_district ->{
                intent= Intent(this,AddDistrictActivity::class.java)
            }
            R.id.action_street ->{
                intent= Intent(this,AddStreetActivity::class.java)
            }
            R.id.action_house ->{
                intent= Intent(this, AddHouseActivity::class.java)
            }
            R.id.action_photo ->{
                intent= Intent(this, AddPhotoActivity::class.java)
            }
            R.id.action_work ->{
                intent= Intent(this,AddWorkActivity::class.java)
            }
            R.id.action_active ->{
                intent= Intent(this,AddActiveActivity::class.java)
            }
        }
        startActivity(intent)
        return true
    }
}
