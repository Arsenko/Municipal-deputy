package com.example.municipaldeputy.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.municipaldeputy.R
import com.example.municipaldeputy.constants.REQUEST_CODE
import com.example.municipaldeputy.service.FileService
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_add_photo.*

class AddPhotoActivity : AppCompatActivity() {
    private lateinit var dbManager: DBManager
    private lateinit var fileService: FileService
    private var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        fileService = FileService(this)
        spinner.adapter =
            ArrayAdapter(
                this,
                R.layout.multiline_spinner_dropdown_item,
                dbManager.fetchHouse(null)
            )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE
            )
        }
        preview_img.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, R.string.permission_error, Toast.LENGTH_LONG).show()
            } else {
                onImageBtnClicked()
            }
        }
        add_btn.setOnClickListener {
            onAddBtnClicked(spinner.selectedItem.toString())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val selectedImageUri: Uri = data?.data!!
            if (requestCode == REQUEST_CODE) {
                preview_img.setImageDrawable(
                    fileService.getDrawable(selectedImageUri)
                )
                path = fileService.getPath(selectedImageUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onImageBtnClicked() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select file to upload "
            ), REQUEST_CODE
        )
    }

    fun onAddBtnClicked(houseAddress: String) {
        if (path != null) {
            DBManager(this).openRead().insertPhoto(path!!, houseAddress)
            Toast.makeText(this, R.string.add_success, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, R.string.pick_photo, Toast.LENGTH_LONG).show()
        }
    }
}