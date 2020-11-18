package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.item_add_district.view.*
import kotlinx.android.synthetic.main.item_add_house.*
import kotlinx.android.synthetic.main.item_add_house.view.*
import kotlinx.android.synthetic.main.item_add_region.view.*

class AddHouseActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_house)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        spinner.adapter =
            ArrayAdapter(
                this,
                R.layout.multiline_spinner_dropdown_item,
                dbManager.fetchStreet(null)
            )
        add_btn.setOnClickListener {
            dbManager.insertHouse(
                House(
                    null, address_edt.text.toString(),
                    Integer.parseInt(number_of_entrances_edt.text.toString()),
                    Integer.parseInt(number_of_floors_edt.text.toString()),
                    management_company_edt.text.toString()
                ),
                spinner.selectedItem.toString()
            )
        }
    }
}