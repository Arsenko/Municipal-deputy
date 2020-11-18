package com.example.municipaldeputy.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.municipaldeputy.R
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        init()
    }

    private fun init() {
        browse_btn.setOnClickListener {
            startActivity(Intent(this,PrimaryChoiseActivity::class.java))
        }
        add_btn.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }
    }
}