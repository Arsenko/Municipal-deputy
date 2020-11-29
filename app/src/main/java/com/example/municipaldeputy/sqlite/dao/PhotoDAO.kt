package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.PHOTO_TABLE
import com.example.municipaldeputy.constants.P_KEY_HOUSE_ID
import com.example.municipaldeputy.constants.P_KEY_ID
import com.example.municipaldeputy.entity.PhotoLink

@Dao
interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(photoLink: PhotoLink) :Long

    @Query("SELECT * FROM $PHOTO_TABLE ORDER BY $P_KEY_ID ASC")
    fun readAllDataSync(): List<PhotoLink>

    @Query("SELECT * FROM $PHOTO_TABLE WHERE $P_KEY_HOUSE_ID = :houseId")
    fun getDataWithHouseIdSync(houseId:Int) : List<PhotoLink>
}