package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.*
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.time.LocalDate


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

    fun fetchPhoto(houseId: Int): List<PhotoLink> {
        var photoLinkList = mutableListOf<PhotoLink>()
        val cursor: Cursor? = database!!.query(
            PHOTO_TABLE,
            arrayOf(
                P_KEY_ID,
                P_KEY_FILEPATH
            ),
            "$P_KEY_HOUSE_ID = $houseId",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    photoLinkList.add(
                        PhotoLink(
                            cursor.getInt(cursor.getColumnIndex(P_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(P_KEY_FILEPATH))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return photoLinkList.toList()
    }

    fun fetchActive(houseId: Int): List<Human> {
        var activeList = mutableListOf<Human>()
        val cursor: Cursor? = database!!.query(
            ASSETS_TABLE,
            arrayOf(
                A_KEY_ID,
                A_KEY_SURNAME,
                A_KEY_NAME,
                A_KEY_PATRONYMIC,
                A_KEY_APARTMENT_NUMBER,
                A_KEY_PHONE_NUMBER,
                A_KEY_MAIL
            ),
            "$A_KEY_HOUSE_ID = $houseId",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    activeList.add(
                        Human(
                            cursor.getInt(cursor.getColumnIndex(A_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(A_KEY_SURNAME)),
                            cursor.getString(cursor.getColumnIndex(A_KEY_NAME)),
                            cursor.getString(cursor.getColumnIndex(A_KEY_PATRONYMIC)),
                            cursor.getInt(cursor.getColumnIndex(A_KEY_APARTMENT_NUMBER)),
                            cursor.getString(cursor.getColumnIndex(A_KEY_PHONE_NUMBER)),
                            cursor.getString(cursor.getColumnIndex(A_KEY_MAIL)),
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return activeList.toList()
    }

    fun fetchWork(houseId: Int, done: Boolean): List<Work> {
        var activeList = mutableListOf<Work>()
        val cursor = when (done) {
            true -> {
                database!!.query(
                    WORK_TABLE,
                    arrayOf(
                        W_KEY_ID,
                        W_KEY_NAME,
                        W_KEY_DATE,
                        W_KEY_WORKER
                    ),
                    "$W_KEY_HOUSE_ID = $houseId AND $W_KEY_STATE=1",
                    null,
                    null,
                    null,
                    null
                )
            }
            false -> {
                database!!.query(
                    WORK_TABLE,
                    arrayOf(
                        W_KEY_ID,
                        W_KEY_NAME,
                        W_KEY_DATE,
                        W_KEY_WORKER
                    ),
                    "$W_KEY_HOUSE_ID = $houseId AND $W_KEY_STATE=0",
                    null,
                    null,
                    null,
                    null
                )
            }
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    activeList.add(
                        Work(
                            cursor.getInt(cursor.getColumnIndex(W_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(W_KEY_NAME)),
                            SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex(W_KEY_DATE))),
                            cursor.getString(cursor.getColumnIndex(W_KEY_WORKER))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return activeList.toList()
    }
}