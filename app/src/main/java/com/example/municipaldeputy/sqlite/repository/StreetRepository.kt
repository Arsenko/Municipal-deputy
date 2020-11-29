package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.Street
import com.example.municipaldeputy.sqlite.dao.StreetDAO

class StreetRepository(private val streetDAO:StreetDAO) {
    fun readAllData() = streetDAO.readAllData()

    fun readDataWithDistrictId(districtId:Int)=streetDAO.readDataWithDistrictId(districtId)

    suspend fun addStreet(street: Street) = streetDAO.add(street)

    fun getIdByName(streetName: String) = streetDAO.getIdByName(streetName)
}