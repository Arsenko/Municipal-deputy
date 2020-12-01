package com.example.municipaldeputy.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.municipaldeputy.R
import com.example.municipaldeputy.activity.add.*
import com.example.municipaldeputy.adapter.PhotoAdapter
import com.example.municipaldeputy.constants.REQUEST_CODE
import com.example.municipaldeputy.service.FileService
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : FragmentActivity() {
    private lateinit var fileService:FileService
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE
            )
        }//можно не принять, тогда будут только ресурсы
        if (intent.hasExtra("id")) {
            fileService= FileService(applicationContext,roomViewModel)
            val list=fileService.getPhotoList(intent.getIntExtra("id",-1))
            container.adapter= PhotoAdapter(list)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var intent: Intent?=null
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