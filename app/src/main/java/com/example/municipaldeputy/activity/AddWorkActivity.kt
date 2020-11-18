package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Work
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_house_list.*
import kotlinx.android.synthetic.main.item_add_district.view.*
import kotlinx.android.synthetic.main.item_add_region.view.*
import kotlinx.android.synthetic.main.item_add_work.*
import kotlinx.android.synthetic.main.item_add_work.view.*
import java.text.SimpleDateFormat

class AddWorkActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_work)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        spinner.adapter =
            ArrayAdapter(
                this,
                R.layout.multiline_spinner_dropdown_item,
                dbManager.fetchHouse(null)
            )
        add_btn.setOnClickListener {
            dbManager.insertWork(
                Work(
                    null,
                    work_name_edt.text.toString(),
                    SimpleDateFormat("dd.mm.yyyy").parse(work_date_edt.text.toString()),
                    construction_company_edt.text.toString()
                ),
                done_chbx.isChecked,
                spinner.selectedItemPosition + 1
            )
        }

    }
}