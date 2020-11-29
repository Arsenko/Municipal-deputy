package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.House

@Dao
interface HouseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(house: House) : Long

    @Query("SELECT * FROM $HOUSE_TABLE ORDER BY $H_KEY_ID ASC")
    fun readAllData(): LiveData<List<House>>

    @Query("SELECT * FROM $HOUSE_TABLE WHERE $H_KEY_STREET_ID = :streetId ORDER BY id ASC")
    fun readDataWithStreetId(streetId: Int): LiveData<List<House>>

    @Query("SELECT * FROM $HOUSE_TABLE WHERE $H_KEY_ADDRESS = :houseAddress ORDER BY id ASC")
    fun getIdByAddress(houseAddress: String): House
}