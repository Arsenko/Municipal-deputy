package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*
import java.util.*

@Entity(
    tableName = WORK_TABLE
)
data class Work(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = W_KEY_ID)
    val id: Int,

    @ColumnInfo(name = W_KEY_NAME)
    val name: String,

    @ColumnInfo(name = W_KEY_DATE)
    val date: String,

    @ColumnInfo(name = W_KEY_WORKER)
    val worker: String,

    @ColumnInfo(name = W_KEY_STATE)
    val state: Int,

    @ColumnInfo(name = W_KEY_HOUSE_ID)
    val houseId: Int,
) {

}