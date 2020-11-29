package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.REGION_TABLE
import com.example.municipaldeputy.constants.R_KEY_ID
import com.example.municipaldeputy.constants.R_KEY_NAME
import com.example.municipaldeputy.entity.Region

@Dao
interface RegionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(region:Region): Long

    @Query("SELECT * FROM $REGION_TABLE ORDER BY $R_KEY_ID ASC")
    fun readAllData(): LiveData<List<Region>>

    @Query("SELECT * FROM $REGION_TABLE ORDER BY $R_KEY_ID ASC")
    fun readAllDataSync(): List<Region>

    @Query("SELECT * FROM $REGION_TABLE WHERE $R_KEY_NAME = :regionName LIMIT 1")
    fun getIdByName(regionName: String): Region
}