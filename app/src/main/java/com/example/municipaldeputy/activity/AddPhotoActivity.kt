package com.example.municipaldeputy.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.municipaldeputy.R
import com.example.municipaldeputy.service.FileService
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.item_add_photo.*

class AddPhotoActivity: AppCompatActivity() {
    private lateinit var dbManager: DBManager
    private lateinit var fileService: FileService
    private var path:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_add_photo)
        init()
    }

    private fun init() {
        dbManager = DBManager(this)
        dbManager.openRead()
        fileService= FileService(this)
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
            onImageBtnClicked()
        }
        add_btn.setOnClickListener {
            onAddBtnClicked(spinner.selectedItemPosition + 1)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val selectedImageUri: Uri = data?.data!!
            if (requestCode == REQUEST_CODE) {
                preview_img.setImageDrawable(fileService.getDrawable(selectedImageUri))
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onImageBtnClicked() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,R.string.permission_error,Toast.LENGTH_LONG).show()
        }
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

    fun onAddBtnClicked(houseId:Int) {
        if(path!=null) {
            DBManager(this).openRead().insertPhoto(path!!,houseId)
            Toast.makeText(this,R.string.add_success, Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,R.string.pick_photo, Toast.LENGTH_LONG).show()
        }
    }
}