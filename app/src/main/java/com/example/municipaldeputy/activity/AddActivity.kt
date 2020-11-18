package com.example.municipaldeputy.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.municipaldeputy.R
import com.example.municipaldeputy.adapter.AddAdapter
import com.example.municipaldeputy.sqlite.DBManager
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.item_add_photo.*

const val REQUEST_CODE = 1

class AddActivity : AppCompatActivity(),
    AddAdapter.OnImageBtnClickListener,
    AddAdapter.OnAddBtnClickListener{
    var path:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        init()
    }

    private fun init() {
        container.adapter = AddAdapter(7).apply {
            ImageClickListener = this@AddActivity
            AddBtnClickListener=this@AddActivity
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode === RESULT_OK) {
            val selectedImageUri: Uri = data?.getData()!!
            if (requestCode === REQUEST_CODE) {
                preview_img.setImageDrawable(Drawable.createFromPath(getPath(selectedImageUri)))
                path=getPath(selectedImageUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    override fun onImageBtnClicked() {
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

    override fun onAddBtnClicked(houseId:Int) {
        if(path!=null) {
            DBManager(this).openRead().insertPhoto(path!!,houseId)
            Toast.makeText(this,R.string.add_success,Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,R.string.pick_photo,Toast.LENGTH_LONG).show()
        }
    }


}