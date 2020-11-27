package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.Street

@Dao
interface StreetDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(street: Street)

    @Query("SELECT * FROM $STREET_TABLE ORDER BY $S_KEY_ID ASC")
    fun readAllData(): LiveData<List<Street>>

    @Query("SELECT * FROM $STREET_TABLE WHERE $S_KEY_DISTINCT_ID = :districtId ORDER BY $S_KEY_ID ASC")
    fun readDataWithDistrictId(districtId:Int): LiveData<List<Street>>

    @Query("SELECT * FROM $STREET_TABLE WHERE $S_KEY_NAME = :streetName")
    fun getIdByName(streetName: String): Street
}