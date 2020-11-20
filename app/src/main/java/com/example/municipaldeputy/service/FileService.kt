package com.example.municipaldeputy.service

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager


class FileService(val context: Context) {
    private var dbManager: DBManager = DBManager(context)

    init {
        dbManager.open()
    }

    fun getPhotoList(houseId: Int): List<Drawable> {
        val listOfDrawable = mutableListOf<Drawable>()
        val linkList = dbManager.fetchPhoto(houseId)
        for (item in linkList) {
            if (item.isResource == 1) {
                listOfDrawable.add(
                    ContextCompat.getDrawable(
                        context,
                        context.getResources()
                            .getIdentifier(item.link, "drawable", context.packageName)
                    )!!
                )
            } else {
                var tempdrawable =
                    Drawable.createFromPath(item.link) //проверка на существование картинки
                if (tempdrawable != null) {
                    listOfDrawable.add(tempdrawable)
                }
            }
        }
        return listOfDrawable.toList()
    }

    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = context.getContentResolver().query(uri, projection, null, null, null)!!
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val returnvalue=cursor.getString(column_index)
        cursor.close()
        return returnvalue
    }

    fun getDrawable(selectedImageUri: Uri): Drawable? {
        return Drawable.createFromPath(getPath(selectedImageUri))
    }
}