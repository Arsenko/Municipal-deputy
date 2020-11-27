package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*

@Entity(
    tableName = DISTRICT_TABLE
)
data class District(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = D_KEY_ID)
    val id: Int,

    @ColumnInfo(name = D_KEY_NAME)
    val name: String,

    @ColumnInfo(name = D_KEY_REGION_ID)
    val regionId: Int
) {
    override fun toString(): String {
        return name
    }
}
