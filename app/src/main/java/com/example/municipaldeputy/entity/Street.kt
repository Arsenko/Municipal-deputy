package com.example.municipaldeputy.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.municipaldeputy.constants.*

@Entity(
    tableName = STREET_TABLE
)
data class Street(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = S_KEY_ID)
    val id: Int,

    @ColumnInfo(name = S_KEY_NAME)
    val name: String,

    @ColumnInfo(name = S_KEY_DISTINCT_ID)
    val districtId: Int
) {
    override fun toString(): String {
        return name;
    }
}
