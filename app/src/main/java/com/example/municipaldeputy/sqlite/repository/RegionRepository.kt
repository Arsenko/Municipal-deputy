package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.sqlite.dao.RegionDAO

class RegionRepository(private val regionDAO: RegionDAO) {

    fun readAllData() = regionDAO.readAllData()

    fun readAllDataSync() =regionDAO.readAllDataSync()

    suspend fun addRegion(region: Region) {
        regionDAO.add(region)
    }

    fun getIdByName(regionName: String) = regionDAO.getIdByName(regionName)

}