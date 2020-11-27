package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_street.*

class AddStreetActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_street)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        val spinnerAdapter =
            ArrayAdapter<District>(
                this,
                R.layout.multiline_spinner_dropdown_item,
            )
        roomViewModel.getDistrictData().observe(this, Observer {
            spinnerAdapter.addAll(it)
        })
        spinner.adapter = spinnerAdapter

        add_btn.setOnClickListener {
            addStreet()
        }
    }

    private fun addStreet() {
        val name = street_name_edt.text.toString()
        val districtId = (roomViewModel.getDistrictIdByName(spinner.selectedItem.toString()) as District).id
        if (inputCheck(name, districtId)) {
            roomViewModel.addStreet(Street(0, name, districtId))
            Toast.makeText(this, getString(R.string.add_success), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, districtId: Int): Boolean {
        return !(name.isEmpty() && districtId < 0)
    }
}