package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.Work

@Dao
interface WorkDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(work: Work):Long

    @Query("SELECT * FROM $WORK_TABLE ORDER BY id ASC")
    fun readAllData(): LiveData<List<Work>>

    @Query("SELECT * FROM $WORK_TABLE WHERE $W_KEY_STATE = 0 AND $W_KEY_HOUSE_ID = :houseId ORDER BY $W_KEY_ID ASC")
    fun readUndoneDataWithHouseId(houseId:Int): LiveData<List<Work>>

    @Query("SELECT * FROM $WORK_TABLE WHERE $W_KEY_STATE = 1 AND $W_KEY_HOUSE_ID = :houseId ORDER BY $W_KEY_ID ASC")
    fun readDoneDataWithHouseId(houseId:Int): LiveData<List<Work>>
}