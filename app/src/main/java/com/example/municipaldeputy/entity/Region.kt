package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.REGION_TABLE
import com.example.municipaldeputy.constants.R_KEY_ID
import com.example.municipaldeputy.constants.R_KEY_NAME

@Entity(
    tableName = REGION_TABLE
)
data class Region(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = R_KEY_ID)
    val id:Int,

    @ColumnInfo(name = R_KEY_NAME)
    val name:String
    ){
    override fun toString(): String {
        return name
    }
}
