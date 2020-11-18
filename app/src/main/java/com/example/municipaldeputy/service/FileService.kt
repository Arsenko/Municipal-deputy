package com.example.municipaldeputy.service

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.municipaldeputy.R
import com.example.municipaldeputy.sqlite.DBManager

class FileService(val context: Context) {
    private var dbManager: DBManager = DBManager(context)
    init{
        dbManager.open()
    }
    fun getPhotoList(houseId:Int):List<Drawable>{
        val listOfDrawable= mutableListOf<Drawable>()
        val linkList=dbManager.fetchPhoto(houseId)
        for(item in linkList){
            if(item.isResource==1){
                listOfDrawable.add(
                    context.getDrawable(
                        context.getResources()
                            .getIdentifier(item.link, "drawable", context.packageName)
                    )!!
                )
            }else{
                Drawable.createFromPath(item.link)?.let { listOfDrawable.add(it) }
            }
        }
        return listOfDrawable.toList()
    }
}