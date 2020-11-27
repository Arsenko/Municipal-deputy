package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.sqlite.dao.DistrictDAO

class DistrictRepository(private val districtDAO: DistrictDAO) {
    fun readAllData() = districtDAO.readAllData()

    fun readDataWithRegionId(regionId:Int) =districtDAO.readDataWithRegionId(regionId)

    suspend fun addDistrict(district: District) {
        districtDAO.add(district)
    }

    fun getIdByName(districtName: String) = districtDAO.getIdByName(districtName)
}