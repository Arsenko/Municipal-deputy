package com.example.municipaldeputy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_primary_choise.*

class PrimaryChoiseActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager
    lateinit var streetlist:List<Street>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary_choise)
        init()
    }

    private fun init() {
        dbManager = DBManager(applicationContext)
        dbManager.open()
        btn_to_house_list.setOnClickListener {
            toHomeListClick()
        }
        val list = dbManager.fetchRegion()
        var regionAdapter = ArrayAdapter(this, R.layout.multiline_spinner_dropdown_item, list)
        regionAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
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
        val districtlist = dbManager.fetchDistrict(id)
        val districtAdapter =
            ArrayAdapter(this, R.layout.multiline_spinner_dropdown_item, districtlist)
        districtAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        val districtSpinner = district_spinner
        districtSpinner.adapter = districtAdapter
        districtSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        streetlist = dbManager.fetchStreet(id)
        val streetAdapter = ArrayAdapter(this, R.layout.multiline_spinner_dropdown_item, streetlist)
        streetAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        val streetSpinner = street_spinner
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
                intent.putExtra("id", streetlist[position].id)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun toHomeListClick() {
        val toHouseList = Intent(applicationContext, HouseListActivity::class.java)
        toHouseList.putExtra("id", intent.getIntExtra("id", -1))
        startActivity(toHouseList)
        dbManager.close()
    }
}