package com.example.municipaldeputy.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.municipaldeputy.constants.ASSETS_TABLE
import com.example.municipaldeputy.constants.A_KEY_HOUSE_ID
import com.example.municipaldeputy.constants.A_KEY_ID
import com.example.municipaldeputy.entity.Human

@Dao
interface HumanDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(human: Human)

    @Query("SELECT * FROM $ASSETS_TABLE ORDER BY $A_KEY_ID ASC")
    fun readAllData(): LiveData<List<Human>>

    @Query("SELECT * FROM $ASSETS_TABLE WHERE $A_KEY_HOUSE_ID = :id ORDER BY $A_KEY_ID ASC")
    fun readDataWithHouseId(id:Int): LiveData<List<Human>>
}