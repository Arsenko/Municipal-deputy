package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.item_add_district.*

class AddDistrictActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_district)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        spinner.adapter =
            ArrayAdapter(
                this,
                R.layout.multiline_spinner_dropdown_item,
                dbManager.fetchRegion()
            )
        add_btn.setOnClickListener {
            dbManager.insertDistrict(
                district_name_edt.text.toString(),
                spinner.selectedItemPosition + 1//пока нет удаления = id
            )
        }

    }
}