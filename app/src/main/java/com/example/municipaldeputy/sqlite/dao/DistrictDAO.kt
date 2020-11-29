package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.District

@Dao
interface DistrictDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(district: District):Long

    @Query("SELECT * FROM $DISTRICT_TABLE ORDER BY id ASC")
    fun readAllData(): LiveData<List<District>>

    @Query("SELECT * FROM $DISTRICT_TABLE WHERE $D_KEY_REGION_ID = :regionId ORDER BY id ASC")
    fun readDataWithRegionId(regionId:Int): LiveData<List<District>>

    @Query("SELECT * FROM $DISTRICT_TABLE WHERE $D_KEY_NAME = :districtName")
    fun getIdByName(districtName : String) : District
}