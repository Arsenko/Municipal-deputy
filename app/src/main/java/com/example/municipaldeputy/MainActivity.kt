package com.example.municipaldeputy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.sqlite.DBHelper
import com.example.municipaldeputy.sqlite.DBManager
import com.example.municipaldeputy.sqlite.REGION_TABLE
import com.example.municipaldeputy.sqlite.R_KEY_NAME
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var dbManager: DBManager
    var regionId=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        dbManager=DBManager(applicationContext)
        dbManager.open()
        val list=dbManager.fetchR()
        var regionAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,list)
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val regionSpinner=region_spinner
        regionSpinner.adapter=regionAdapter
        regionSpinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position) as Region
                regionId=selectedItem.id
                refreshAdapters()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun refreshAdapters() {
        val districtlist=dbManager.fetchD(regionId)
        val districtAdapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,districtlist)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val districtSpinner=district_spinner
        districtSpinner.adapter=districtAdapter
    }
}