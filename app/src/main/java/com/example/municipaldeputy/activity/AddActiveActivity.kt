package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Human
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.item_add_active.*

class AddActiveActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_active)
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
            dbManager.insertActive(
                Human(
                    null,
                    surname_edt.text.toString(),
                    name_edt.text.toString(),
                    patronymic_edt.text.toString(),
                    Integer.parseInt(apartment_number_edt.text.toString()),
                    phone_number_edt.text.toString(),
                    mail_edt.text.toString()
                ),
                spinner.selectedItemPosition + 1
            )
        }

    }
}