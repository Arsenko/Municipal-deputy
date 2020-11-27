package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*

@Entity(
    tableName = ASSETS_TABLE
)
data class Human(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = A_KEY_ID)
    val id: Int,

    @ColumnInfo(name = A_KEY_SURNAME)
    val surname: String,

    @ColumnInfo(name = A_KEY_NAME)
    val name: String,

    @ColumnInfo(name = A_KEY_PATRONYMIC)
    val patronymic: String,

    @ColumnInfo(name = A_KEY_APARTMENT_NUMBER)
    val apartmentNumber: Int,

    @ColumnInfo(name = A_KEY_PHONE_NUMBER)
    val phoneNumber: String,

    @ColumnInfo(name = A_KEY_MAIL)
    val mail: String,

    @ColumnInfo(name = A_KEY_HOUSE_ID)
    val houseId: Int
) {
    override fun toString(): String {
        return "$surname $name $patronymic"
    }
}