package com.example.municipaldeputy.sqlite.repository

import com.example.municipaldeputy.entity.Human
import com.example.municipaldeputy.sqlite.dao.HumanDAO

class ActiveRepository(private val humanDAO : HumanDAO) {
    fun readAllData() = humanDAO.readAllData()

    fun readDataWithHouseId(id:Int) = humanDAO.readDataWithHouseId(id)

    suspend fun addActive(human: Human) = humanDAO.add(human)
}