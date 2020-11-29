package com.example.municipaldeputy.activity.add

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.municipaldeputy.R
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.entity.Work
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_street.*
import kotlinx.android.synthetic.main.activity_add_work.*
import kotlinx.android.synthetic.main.activity_add_work.add_btn
import kotlinx.android.synthetic.main.activity_add_work.spinner
import kotlinx.android.synthetic.main.activity_add_work.work_date_vtext
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddWorkActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var roomViewModel: RoomViewModel
    private var selectedDate = Date()
    private var dialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        updateDate()
        var spinnerAdapter =
            ArrayAdapter<House>(
                this,
                R.layout.multiline_spinner_dropdown_item
            )

        roomViewModel.getHouseData().observe(this, Observer {
            spinnerAdapter.addAll(it)
        }
        )
        spinner.adapter = spinnerAdapter
        add_btn.setOnClickListener {
            addClick()
        }
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"))
        cal.time = selectedDate
        val datePickerDialog = DatePickerDialog(
            this,
            this@AddWorkActivity,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        change_btn.setOnClickListener {
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val tempCalendar = Calendar.getInstance()
        with(tempCalendar) {
            this.set(Calendar.YEAR, year)
            this.set(Calendar.MINUTE, month)
            this.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        selectedDate = tempCalendar.time
        updateDate()
    }

    private fun updateDate() {
        work_date_vtext.text = SimpleDateFormat("dd.M.yyyy").format(selectedDate)
    }

    private fun addClick() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddWorkActivity).apply {
                setMessage(this@AddWorkActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert: Long? = null
            val workName = work_name_edt.text.toString()
            val workDate = work_date_vtext.text.toString()
            val worker = construction_company_edt.text.toString()

            val workdone = if (done_chbx.isChecked) {
                1
            } else {
                0
            }
            val houseId = (roomViewModel.getHouseIdByName(spinner.selectedItem.toString())).id
            if (inputCheck(workName, workDate, worker, houseId)) {
                resultInsert=roomViewModel.addWork(Work(0, workName, workDate, worker, workdone, houseId))
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddWorkActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddWorkActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(
        workName: String,
        workDate: String,
        worker: String,
        houseId: Int
    ): Boolean {
        return !(workName.isEmpty() && workDate.isEmpty() && worker.isEmpty() && houseId < 0)
    }
}