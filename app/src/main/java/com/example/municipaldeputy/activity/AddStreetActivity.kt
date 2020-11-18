package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.item_add_district.view.*
import kotlinx.android.synthetic.main.item_add_region.view.*
import kotlinx.android.synthetic.main.item_add_street.*
import kotlinx.android.synthetic.main.item_add_street.view.*

class AddStreetActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_street)
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
                spinner.selectedItemPosition + 1
            )
        }


    }
}