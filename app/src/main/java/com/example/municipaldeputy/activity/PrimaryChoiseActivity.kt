package com.example.municipaldeputy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.municipaldeputy.R
import com.example.municipaldeputy.activity.add.*
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_primary_choise.*

class PrimaryChoiseActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_choise)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        btn_to_house_list.setOnClickListener {
            toHomeListClick()
        }
        var regionAdapter = ArrayAdapter<Region>(
            this,
            R.layout.multiline_spinner_item
        )
        regionAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        roomViewModel.getRegionData().observe(this, Observer {
            regionAdapter.addAll(it as List<Region>)
        })
        val regionSpinner = region_spinner
        regionSpinner.adapter = regionAdapter
        regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position) as Region
                refreshDistrict(selectedItem.id!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun refreshDistrict(id: Int) {
        val districtAdapter =
            ArrayAdapter<District>(
                this,
                R.layout.multiline_spinner_item
            )
        districtAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        roomViewModel.getDistrictByRegionId(id).observe(this, Observer {
            districtAdapter.addAll(it as List<District>)
        })
        val districtSpinner = district_spinner
        districtSpinner.adapter = districtAdapter
        districtSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position) as District
                    refreshStreet(selectedItem.id!!)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun refreshStreet(id: Int) {
        val streetAdapter = ArrayAdapter<Street>(
            this,
            R.layout.multiline_spinner_item
        )
        streetAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        val streetSpinner = street_spinner

        roomViewModel.getStreetWithDistrictId(id).observe(this, Observer {
            streetAdapter.addAll(it as List<Street>)
        })
        streetSpinner.adapter = streetAdapter
        streetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (intent.hasExtra("id")) {
                    intent.removeExtra("id")
                }
                val selectedItem = parent?.selectedItem as Street
                intent.putExtra("id", selectedItem.id!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun toHomeListClick() {
        val toHouseList = Intent(applicationContext, HouseListActivity::class.java)
        toHouseList.putExtra("id", intent.getIntExtra("id", -1))
        startActivity(toHouseList)
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
