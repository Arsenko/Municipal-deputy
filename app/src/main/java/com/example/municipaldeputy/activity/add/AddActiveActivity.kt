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
import com.example.municipaldeputy.entity.Human
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_active.*
import kotlinx.android.synthetic.main.activity_add_active.add_btn
import kotlinx.android.synthetic.main.activity_add_active.spinner
import kotlinx.coroutines.launch

class AddActiveActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel
    private var dialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_active)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        var spinnerAdapter = ArrayAdapter<House>(
            this,
            R.layout.multiline_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        roomViewModel.getHouseData().observe(this,
            Observer {
                spinnerAdapter.addAll(it)
            }
        )
        spinner.adapter = spinnerAdapter
        add_btn.setOnClickListener {
            addHuman()
        }

    }

    private fun addHuman() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddActiveActivity).apply {
                setMessage(this@AddActiveActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert: Long? = null
            val surname = surname_edt.text.toString()
            val name = name_edt.text.toString()
            val patronymic = patronymic_edt.text.toString()
            val apartmentNumber = Integer.parseInt(apartment_number_edt.text.toString())
            val phone = phone_number_edt.text.toString()
            val mail = mail_edt.text.toString()
            val houseId =
                (roomViewModel.getHouseIdByName(spinner.selectedItem.toString())).id
            if (inputCheck(surname, name, patronymic, apartmentNumber, phone, mail, houseId)) {
                resultInsert = roomViewModel.addActive(
                    Human(
                        0,
                        surname,
                        name,
                        patronymic,
                        apartmentNumber,
                        phone,
                        mail,
                        houseId
                    )
                )
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(
                    this@AddActiveActivity,
                    getString(R.string.add_success),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@AddActiveActivity,
                    getString(R.string.incorrect_input),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun inputCheck(
        surname: String,
        name: String,
        patronymic: String,
        apartmentNumber: Int,
        phone: String,
        mail: String,
        houseId: Int
    ): Boolean {
        return !(surname.isEmpty() && name.isEmpty() && patronymic.isEmpty()
                && apartmentNumber < 0 && phone.isEmpty() && mail.isEmpty() && houseId < 0)
    }
}