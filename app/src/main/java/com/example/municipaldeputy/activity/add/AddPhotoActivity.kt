package com.example.municipaldeputy.activity.add

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.municipaldeputy.R
import com.example.municipaldeputy.constants.REQUEST_CODE
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.PhotoLink
import com.example.municipaldeputy.service.FileService
import com.example.municipaldeputy.sqlite.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_photo.*
import kotlinx.android.synthetic.main.activity_add_photo.add_btn
import kotlinx.android.synthetic.main.activity_add_photo.spinner
import kotlinx.coroutines.launch

class AddPhotoActivity : AppCompatActivity() {
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var fileService: FileService
    private var path: String? = null
    private var dialog: ProgressDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        init()
    }

    private fun init() {
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        fileService = FileService(this,roomViewModel)
        val spinnerAdapter =
            ArrayAdapter<House>(
                this,
                R.layout.multiline_spinner_item
            )
        spinnerAdapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item)
        roomViewModel.getHouseData().observe(this,
            Observer {
                spinnerAdapter.addAll(it)
            }
        )
        spinner.adapter=spinnerAdapter
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
            onAddBtnClicked()
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

    private fun onImageBtnClicked() {
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

    private fun onAddBtnClicked() {
        lifecycleScope.launch {
            dialog = ProgressDialog(this@AddPhotoActivity).apply {
                setMessage(this@AddPhotoActivity.getString(R.string.please_wait))
                setTitle(R.string.adding_entity)
                setCancelable(false)
                setProgressBarIndeterminate(true)
                show()
            }
            var resultInsert:Long?=null
            val link=path!!
            val houseId= (roomViewModel.getHouseIdByName(spinner.selectedItem.toString())).id
            if (path != null) {
                resultInsert=roomViewModel.addPhoto(PhotoLink(0,link,0, houseId))
            }
            dialog?.dismiss()
            if (resultInsert != null) {
                Toast.makeText(this@AddPhotoActivity, getString(R.string.add_success), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@AddPhotoActivity, getString(R.string.incorrect_input), Toast.LENGTH_LONG).show()
            }
        }
    }
}