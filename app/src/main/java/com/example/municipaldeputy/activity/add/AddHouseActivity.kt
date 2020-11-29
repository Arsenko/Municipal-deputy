package com.example.municipaldeputy.activity.add

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_house.*
import kotlinx.android.synthetic.main.activity_add_house.add_btn
import kotlinx.android.synthetic.main.activity_add_house.spinner
import kotlinx.coroutines.launch

class AddHouseActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel
    private var dialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        var spinneradapter =
            ArrayAdapter<Street>(
                this,
                R.layout.multiline_spinner_item
            )
        spinneradapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)

        roomViewModel.getStreetData().observe(this, Observer {
            spinneradapter.addAll(it)
            spinneradapter.notifyDataSetChanged()
        })
        spinner.adapter = spinneradapter
        add_btn.setOnClickListener {
            addHouse()
        }
    }

    private fun addHouse() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddHouseActivity).apply {
                setMessage(this@AddHouseActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert:Long?=null
            val address = address_edt.text.toString()
            val entrancesCount: Int = Integer.parseInt(number_of_entrances_edt.text.toString())
            val floorCount: Int = Integer.parseInt(number_of_floors_edt.text.toString())
            val managementCompany: String = management_company_edt.text.toString()
            val streetId: Int = roomViewModel.getStreetIdByName(spinner.selectedItem.toString()).id
            if (inputCheck(address, entrancesCount, floorCount, managementCompany, streetId)) {
                resultInsert=roomViewModel.addHouse(
                    House(
                        0,
                        address,
                        entrancesCount,
                        floorCount,
                        managementCompany,
                        streetId
                    )
                )
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddHouseActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddHouseActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(address: String, entrancesCount: Int, floorCount: Int, managementCompany: String, streetId: Int): Boolean {
        return !(address.isEmpty() && entrancesCount<0 && floorCount<0 && managementCompany.isEmpty() && streetId<0)
    }
}