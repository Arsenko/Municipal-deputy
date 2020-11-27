package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*

@Entity(
    tableName = PHOTO_TABLE
)
data class PhotoLink(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = P_KEY_ID)
    val id: Int,

    @ColumnInfo(name = P_KEY_FILEPATH)
    val link: String,

    @ColumnInfo(name = P_KEY_IS_RESOURCE)
    val isResource: Int,

    @ColumnInfo(name = P_KEY_HOUSE_ID)
    val houseId: Int
) {
    override fun toString(): String {
        return link
    }
}