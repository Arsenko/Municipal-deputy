package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.Region
import java.sql.SQLException


class DBManager(c: Context) {
    private val context: Context = c
    private var database: SQLiteDatabase? = null
    private var dbHelper: DBHelper? = null
    @Throws(SQLException::class)
    fun open(): DBManager {
        dbHelper = DBHelper(context)
        database = dbHelper!!.getWritableDatabase()
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun insert(name: String?) {
        val contentValue = ContentValues()
        contentValue.put(R_KEY_NAME, name)
        database!!.insert(REGION_TABLE, null, contentValue)
    }

    fun fetchR(): List<Region> {
        var regionlist= mutableListOf<Region>()
        val cursor: Cursor? = database!!.query(
            REGION_TABLE,
            arrayOf(R_KEY_ID, R_KEY_NAME),
            null,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    regionlist.add(Region(cursor.getInt(cursor.getColumnIndex(R_KEY_ID)),cursor.getString(cursor.getColumnIndex(R_KEY_NAME))))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return regionlist.toList()
    }

    fun fetchD(regionId:Int): List<District>{
        var districtlist= mutableListOf<District>()
        val cursor: Cursor? = database!!.query(
            DISTINCT_TABLE,
            arrayOf(D_KEY_ID, D_KEY_NAME),
            "$D_KEY_REGION_ID = $regionId",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    districtlist.add(District(cursor.getInt(cursor.getColumnIndex(D_KEY_ID)),cursor.getString(cursor.getColumnIndex(D_KEY_NAME))))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return districtlist.toList()
    }

}