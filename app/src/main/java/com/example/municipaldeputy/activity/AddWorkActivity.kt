package com.example.municipaldeputy.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Work
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_add_work.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AddWorkActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var dbManager: DBManager
    var selectedDate=Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        init()
    }

    private fun init() {
        updateDate()
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
                    selectedDate,
                    construction_company_edt.text.toString()
                ),
                done_chbx.isChecked,
                spinner.selectedItem.toString()
            )
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

    fun updateDate() {
        work_date_vtext.text = SimpleDateFormat("dd.M.yyyy").format(selectedDate)
    }
}