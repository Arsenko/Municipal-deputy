package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.District
import com.example.municipaldeputy.entity.House
import com.example.municipaldeputy.entity.Region
import com.example.municipaldeputy.entity.Street
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

    fun fetchRegion(): List<Region> {
        var regionlist = mutableListOf<Region>()
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
                    regionlist.add(
                        Region(
                            cursor.getInt(cursor.getColumnIndex(R_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(R_KEY_NAME))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return regionlist.toList()
    }

    fun fetchDistrict(regionId: Int): List<District> {
        var districtlist = mutableListOf<District>()
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
                    districtlist.add(
                        District(
                            cursor.getInt(cursor.getColumnIndex(D_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(D_KEY_NAME))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return districtlist.toList()
    }

    fun fetchStreet(districtId: Int): List<Street> {
        var streetlist = mutableListOf<Street>()
        val cursor: Cursor? = database!!.query(
            STREET_TABLE,
            arrayOf(S_KEY_ID, S_KEY_NAME),
            "$S_KEY_DISTINCT_ID = $districtId",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    streetlist.add(
                        Street(
                            cursor.getInt(cursor.getColumnIndex(S_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(S_KEY_NAME))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return streetlist.toList()
    }

    fun fetchHouse(streetId: Int): MutableList<House> {
        var houselist = mutableListOf<House>()
        val cursor: Cursor? = database!!.query(
            HOUSE_TABLE,
            arrayOf(
                H_KEY_ID,
                H_KEY_ADDRESS,
                H_KEY_NUMBER_OF_ENTRANCES,
                H_KEY_NUMBER_OF_FLOORS,
                H_KEY_MANAGEMENT_COMPANY
            ),
            "$H_KEY_STREET_ID = $streetId",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    houselist.add(
                        House(
                            cursor.getInt(cursor.getColumnIndex(H_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(H_KEY_ADDRESS)),
                            cursor.getInt(cursor.getColumnIndex(H_KEY_NUMBER_OF_ENTRANCES)),
                            cursor.getInt(cursor.getColumnIndex(H_KEY_NUMBER_OF_FLOORS)),
                            cursor.getString(cursor.getColumnIndex(H_KEY_MANAGEMENT_COMPANY))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return houselist
    }
}