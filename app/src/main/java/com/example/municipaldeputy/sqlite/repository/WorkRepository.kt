package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.Work
import com.example.municipaldeputy.sqlite.dao.WorkDAO

class WorkRepository(private val workDAO: WorkDAO) {
    fun readAllData() = workDAO.readAllData()

    suspend fun addWork(work: Work) = workDAO.add(work)

    fun readUndoneDataWithHouseID(houseId: Int) = workDAO.readUndoneDataWithHouseId(houseId)

    fun readDoneDataWithHouseID(houseId: Int) = workDAO.readDoneDataWithHouseId(houseId)
}