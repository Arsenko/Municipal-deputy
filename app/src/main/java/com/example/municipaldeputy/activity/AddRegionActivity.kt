package com.example.municipaldeputy.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.municipaldeputy.R
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_region.*

class AddRegionActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel

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

    private fun addRegion(){
        val regionName=region_name_edt.text.toString()
        if (inputCheck(regionName)) {
            roomViewModel.addRegion(Region(0, regionName))
            Toast.makeText(this, getString(R.string.add_success), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
        }
    }

    fun inputCheck(regionName:String):Boolean{
        return regionName.isNotEmpty()
    }
}