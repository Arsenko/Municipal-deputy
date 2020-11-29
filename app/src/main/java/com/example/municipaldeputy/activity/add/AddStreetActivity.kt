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
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_region.*
import kotlinx.android.synthetic.main.activity_add_street.*
import kotlinx.android.synthetic.main.activity_add_street.add_btn
import kotlinx.coroutines.launch

class AddStreetActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel
    private var dialog:ProgressDialog?=null

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
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddStreetActivity).apply {
                setMessage(this@AddStreetActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert: Long? = null
            val name = street_name_edt.text.toString()
            val districtId = (roomViewModel.getDistrictIdByName(spinner.selectedItem.toString())).id
            if (inputCheck(name, districtId)) {
                resultInsert =roomViewModel.addStreet(Street(0, name, districtId))
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddStreetActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddStreetActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(name: String, districtId: Int): Boolean {
        return !(name.isEmpty() && districtId < 0)
    }
}