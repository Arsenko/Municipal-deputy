package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*

@Entity(
    tableName = HOUSE_TABLE
)
data class House(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= H_KEY_ID)
    val id:Int,

    @ColumnInfo(name= H_KEY_ADDRESS)
    val address:String,

    @ColumnInfo(name = H_KEY_NUMBER_OF_ENTRANCES)
    val entrancesCount:Int,

    @ColumnInfo(name = H_KEY_NUMBER_OF_FLOORS)
    val floorCount:Int,

    @ColumnInfo(name = H_KEY_MANAGEMENT_COMPANY)
    val managementCompany:String,

    @ColumnInfo(name = H_KEY_STREET_ID)
    val streetId:Int
) {
    override fun toString(): String {
        return address
    }
}