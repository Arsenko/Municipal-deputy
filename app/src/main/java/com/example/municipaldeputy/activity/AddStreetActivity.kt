package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_add_street.*

class AddStreetActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_street)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        spinner.adapter =
            ArrayAdapter(
                this,
                R.layout.multiline_spinner_dropdown_item,
                dbManager.fetchDistrict(null)
            )
        add_btn.setOnClickListener {
            dbManager.insertStreet(
                street_name_edt.text.toString(),
                spinner.selectedItem.toString()
            )
        }


    }
}