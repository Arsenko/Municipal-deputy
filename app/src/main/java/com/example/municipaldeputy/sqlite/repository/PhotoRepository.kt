package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.PhotoLink
import com.example.municipaldeputy.sqlite.dao.PhotoDAO

class PhotoRepository(private val photoDAO: PhotoDAO) {
    fun readAllData() = photoDAO.readAllDataSync()

    fun getDataWithHouseIdSync(houseId:Int)=photoDAO.getDataWithHouseIdSync(houseId)

    suspend fun addPhoto(photoLink: PhotoLink) =
        photoDAO.add(photoLink)

}