package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.sqlite.dao.HouseDAO

class HouseRepository(private val houseDAO: HouseDAO) {
    fun readAllData() = houseDAO.readAllData()

    suspend fun addHouse(house: House): Long {
        return houseDAO.add(house)
    }

    fun readDataWithStreetId(streetId:Int)=houseDAO.readDataWithStreetId(streetId)

    fun getIdByName(houseAddress: String) = houseDAO.getIdByAddress(houseAddress)
}