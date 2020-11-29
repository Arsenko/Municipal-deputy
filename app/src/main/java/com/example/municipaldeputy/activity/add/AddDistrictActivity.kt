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
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_district.*
import kotlinx.android.synthetic.main.activity_add_district.add_btn
import kotlinx.coroutines.launch

class AddDistrictActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_district)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        var spinnerAdapter =
            ArrayAdapter<Region>(
                this,
                R.layout.multiline_spinner_item
            )
        spinnerAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        roomViewModel.getRegionData().observe(this, Observer {
            spinnerAdapter.addAll(it)
        })
        spinner.adapter = spinnerAdapter

        add_btn.setOnClickListener {
            addDistrict()
        }
    }

    private fun addDistrict() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddDistrictActivity).apply {
                setMessage(this@AddDistrictActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert: Long? = null
            val name = district_name_edt.text.toString()
            val regionId = roomViewModel.getRegionIdByName(spinner.selectedItem.toString()).id
            if (inputCheck(name, regionId)) {
                resultInsert = roomViewModel.addDistrict(District(0, name, regionId))
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddDistrictActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddDistrictActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(name: String, regionId: Int?): Boolean {
        return !(name.isEmpty() && regionId != null && regionId < 0)
    }
}


