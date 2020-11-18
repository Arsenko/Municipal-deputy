package com.example.municipaldeputy.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(){
    var path:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        init()
    }

    private fun init() {
        add_region_btn.setOnClickListener {
            startActivity(Intent(this,AddRegionActivity::class.java))
        }
        add_district_btn.setOnClickListener {
            startActivity(Intent(this,AddDistrictActivity::class.java))
        }
        add_street_btn.setOnClickListener {
            startActivity(Intent(this,AddStreetActivity::class.java))
        }
        add_house_btn.setOnClickListener {
            startActivity(Intent(this,AddHouseActivity::class.java))
        }
        add_photo_btn.setOnClickListener {
            startActivity(Intent(this,AddPhotoActivity::class.java))
        }
        add_work_btn.setOnClickListener {
            startActivity(Intent(this,AddWorkActivity::class.java))
        }
        add_asset_btn.setOnClickListener {
            startActivity(Intent(this,AddActiveActivity::class.java))
        }
    }
}