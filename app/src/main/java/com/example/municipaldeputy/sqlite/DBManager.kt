package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.municipaldeputy.R
import com.example.municipaldeputy.constants.*
import com.example.municipaldeputy.entity.*
import java.sql.Date
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
        database = dbHelper!!.writableDatabase
        return this
    }

    fun openRead(): DBManager {
        dbHelper = DBHelper(context)
        database = dbHelper!!.readableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
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

    fun fetchRegion(regionName: String): Boolean {
        val cursor: Cursor? = database!!.query(
            REGION_TABLE,
            arrayOf(R_KEY_ID, R_KEY_NAME),
            "$R_KEY_NAME = \"$regionName\"",
            null,
            null,
            null,
            null
        )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
    }

    fun fetchRegionId(regionName: String): Int {
        val cursor: Cursor? = database!!.query(
            REGION_TABLE,
            arrayOf(R_KEY_ID, R_KEY_NAME),
            "$R_KEY_NAME = \"$regionName\"",
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(R_KEY_ID))
            }
            cursor.close()
        }
        return -1
    }

    fun fetchDistrict(regionId: Int?): List<District> {
        var districtlist = mutableListOf<District>()
        val cursor: Cursor? = if (regionId != null) {
            database!!.query(
                DISTINCT_TABLE,
                arrayOf(D_KEY_ID, D_KEY_NAME),
                "$D_KEY_REGION_ID = $regionId",
                null,
                null,
                null,
                null
            )
        } else {
            database!!.query(
                DISTINCT_TABLE,
                arrayOf(D_KEY_ID, D_KEY_NAME),
                null,
                null,
                null,
                null,
                null
            )
        }
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

    fun fetchDistrict(districtName: String): Boolean {
        val cursor: Cursor? =
            database!!.query(
                DISTINCT_TABLE,
                arrayOf(D_KEY_ID, D_KEY_NAME),
                "$D_KEY_NAME = \"$districtName\"",
                null,
                null,
                null,
                null
            )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
    }

    fun fetchDistrictId(districtName: String): Int {
        val cursor: Cursor? =
            database!!.query(
                DISTINCT_TABLE,
                arrayOf(D_KEY_ID),
                "$D_KEY_NAME = \"$districtName\"",
                null,
                null,
                null,
                null
            )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(D_KEY_ID))
            }
            cursor.close()
        }
        return -1
    }

    fun fetchStreet(districtId: Int?): List<Street> {
        var streetlist = mutableListOf<Street>()
        val cursor: Cursor? = if (districtId != null) {
            database!!.query(
                STREET_TABLE,
                arrayOf(S_KEY_ID, S_KEY_NAME),
                "$S_KEY_DISTINCT_ID = $districtId",
                null,
                null,
                null,
                null
            )
        } else {
            database!!.query(
                STREET_TABLE,
                arrayOf(S_KEY_ID, S_KEY_NAME),
                null,
                null,
                null,
                null,
                null
            )
        }
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

    fun fetchStreet(streetName: String): Boolean {
        val cursor: Cursor? =
            database!!.query(
                STREET_TABLE,
                arrayOf(S_KEY_NAME),
                "$S_KEY_NAME = \"$streetName\"",
                null,
                null,
                null,
                null
            )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
    }

    fun fetchStreetById(streetName: String): Int {
        val cursor: Cursor? =
            database!!.query(
                STREET_TABLE,
                arrayOf(S_KEY_ID),
                "$S_KEY_NAME = \"$streetName\"",
                null,
                null,
                null,
                null
            )
        if (cursor!!.count != 0) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(S_KEY_ID))
            }
            cursor.close()
        }
        return -1
    }

    fun fetchHouse(streetId: Int?): MutableList<House> {
        var houselist = mutableListOf<House>()
        val cursor: Cursor? = if (streetId != null) {
            database!!.query(
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
        } else {
            database!!.query(
                HOUSE_TABLE,
                arrayOf(
                    H_KEY_ID,
                    H_KEY_ADDRESS,
                    H_KEY_NUMBER_OF_ENTRANCES,
                    H_KEY_NUMBER_OF_FLOORS,
                    H_KEY_MANAGEMENT_COMPANY
                ),
                null,
                null,
                null,
                null,
                null
            )
        }
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

    fun fetchHouse(HouseName: String): Boolean {
        val cursor: Cursor? =
            database!!.query(
                HOUSE_TABLE,
                arrayOf(
                    H_KEY_ID,
                    H_KEY_ADDRESS,
                    H_KEY_NUMBER_OF_ENTRANCES,
                    H_KEY_NUMBER_OF_FLOORS,
                    H_KEY_MANAGEMENT_COMPANY
                ),
                "$H_KEY_ADDRESS = \"$HouseName\"",
                null,
                null,
                null,
                null
            )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
    }

    fun fetchHouseId(houseAddress: String): Int {
        var id: Int = -1
        val cursor: Cursor? =
            database!!.query(
                HOUSE_TABLE,
                arrayOf(
                    H_KEY_ID
                ),
                "$H_KEY_ADDRESS = \"$houseAddress\"",
                null,
                null,
                null,
                null
            )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex(H_KEY_ID))
                cursor.close()
            }

        }
        return id
    }

    fun fetchPhoto(houseId: Int): List<PhotoLink> {
        var photoLinkList = mutableListOf<PhotoLink>()
        val cursor: Cursor? = database!!.query(
            PHOTO_TABLE,
            arrayOf(
                P_KEY_ID,
                P_KEY_FILEPATH,
                P_KEY_IS_RESOURCE
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
                            cursor.getString(cursor.getColumnIndex(P_KEY_FILEPATH)),
                            cursor.getInt(cursor.getColumnIndex(P_KEY_IS_RESOURCE))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return photoLinkList.toList()
    }

    fun fetchPhoto(link: String): Boolean {
        val cursor: Cursor? = database!!.query(
            PHOTO_TABLE,
            arrayOf(
                P_KEY_ID,
                P_KEY_FILEPATH
            ),
            "$P_KEY_FILEPATH = \"$link\"",
            null,
            null,
            null,
            null
        )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
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

    fun fetchActive(mail: String, phone: String): Boolean {
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
            "$A_KEY_MAIL = \"$mail\" AND $A_KEY_PHONE_NUMBER=\"$phone\"",
            null,
            null,
            null,
            null
        )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
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
                    val str= cursor.getString(
                        cursor.getColumnIndex(
                            W_KEY_DATE
                        )
                    )
                    activeList.add(
                        Work(
                            cursor.getInt(cursor.getColumnIndex(W_KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(W_KEY_NAME)),
                            SimpleDateFormat("yyyy-MM-dd").parse(
                                cursor.getString(
                                    cursor.getColumnIndex(
                                        W_KEY_DATE
                                    )
                                )
                            )!!,
                            cursor.getString(cursor.getColumnIndex(W_KEY_WORKER))
                        )
                    )
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return activeList.toList()
    }

    fun fetchWork(workName: String, houseId: Int): Boolean {
        val cursor = database!!.query(
            WORK_TABLE,
            arrayOf(
                W_KEY_ID,
                W_KEY_NAME,
                W_KEY_DATE,
                W_KEY_WORKER
            ),
            "$W_KEY_HOUSE_ID = $houseId AND $W_KEY_NAME=\"$workName\"",
            null,
            null,
            null,
            null
        )
        if (cursor!!.count != 0) {
            cursor.close()
            return true
        }
        return false
    }

    fun insertRegion(regionName: String) {
        if (fetchRegion(regionName)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(R_KEY_NAME, regionName)
            database!!.insert(REGION_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertDistrict(districtName: String, regionName: String) {
        if (fetchDistrict(districtName)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(D_KEY_REGION_ID, fetchRegionId(regionName))
            contentValue.put(D_KEY_NAME, districtName)
            database!!.insert(DISTINCT_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertStreet(streetName: String, districtName: String) {
        if (fetchStreet(streetName)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(S_KEY_DISTINCT_ID, fetchDistrictId(districtName))
            contentValue.put(S_KEY_NAME, streetName)
            database!!.insert(STREET_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertHouse(house: House, streetName: String) {
        Toast.makeText(this.context, streetName, Toast.LENGTH_LONG).show()
        if (fetchHouse(house.address)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(H_KEY_ADDRESS, house.address)
            contentValue.put(H_KEY_NUMBER_OF_ENTRANCES, house.entrancesCount)
            contentValue.put(H_KEY_NUMBER_OF_FLOORS, house.floorCount)
            contentValue.put(H_KEY_MANAGEMENT_COMPANY, house.managementCompany)
            contentValue.put(H_KEY_STREET_ID, fetchStreetById(streetName))
            database!!.insert(HOUSE_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertWork(
        work: Work,
        workState: Boolean,
        houseAddress: String
    ) {
        val houseId = fetchHouseId(houseAddress)
        if (fetchWork(work.name, houseId)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(W_KEY_NAME, work.name)
            contentValue.put(W_KEY_DATE, SimpleDateFormat("yyyy-MM-dd").format(work.date))
            contentValue.put(W_KEY_WORKER, work.worker)
            when(workState){
                true -> contentValue.put(W_KEY_STATE, 1)
                false -> contentValue.put(W_KEY_STATE, 0)
            }
            contentValue.put(W_KEY_HOUSE_ID,houseId)
            database!!.insert(WORK_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertActive(human: Human, houseAddress: String) {
        if (fetchActive(human.mail, human.phoneNumber)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(A_KEY_NAME, human.name)
            contentValue.put(A_KEY_SURNAME, human.surname)
            contentValue.put(A_KEY_PATRONYMIC, human.patronymic)
            contentValue.put(A_KEY_PHONE_NUMBER, human.phoneNumber)
            contentValue.put(A_KEY_MAIL, human.mail)
            contentValue.put(A_KEY_APARTMENT_NUMBER, human.apartmentNumber)
            contentValue.put(A_KEY_HOUSE_ID, fetchHouseId(houseAddress))
            database!!.insert(ASSETS_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }

    fun insertPhoto(photolink: String, houseAddress: String) {
        if (fetchPhoto(photolink)) {
            Toast.makeText(this.context, R.string.already_exist, Toast.LENGTH_LONG).show()
        } else {
            val contentValue = ContentValues()
            contentValue.put(P_KEY_FILEPATH, photolink)
            contentValue.put(P_KEY_HOUSE_ID, fetchHouseId(houseAddress))
            contentValue.put(P_KEY_IS_RESOURCE, 0)
            database!!.insert(PHOTO_TABLE, null, contentValue)
            Toast.makeText(this.context, R.string.add_success, Toast.LENGTH_LONG).show()
        }
    }
}