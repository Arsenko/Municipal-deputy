package com.example.municipaldeputy.activity.add

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_region.*
import kotlinx.coroutines.launch

class AddRegionActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_region)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        add_btn.setOnClickListener {
            addRegion()
        }
    }

    private fun addRegion() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddRegionActivity).apply {
                setMessage(this@AddRegionActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert: Long? = null
            val regionName = region_name_edt.text.toString()
            if (inputCheck(regionName)) {
                resultInsert = roomViewModel.addRegion(Region(0, regionName))
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddRegionActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddRegionActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(regionName: String): Boolean {
        return regionName.isNotEmpty()
    }
}