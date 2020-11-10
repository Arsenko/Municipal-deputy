package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "city structure"
const val DB_VERSION = 4


const val REGION_TABLE = "region"
const val R_KEY_ID = "_id"
const val R_KEY_NAME = "name"

const val DISTINCT_TABLE = "district"
const val D_KEY_ID = "_id"
const val D_KEY_NAME = "name"
const val D_KEY_REGION_ID = "region_id"

const val STREET_TABLE = "street"
const val S_KEY_ID = "_id"
const val S_KEY_NAME = "name"
const val S_KEY_DISTINCT_ID = "street_id"

const val HOUSE_TABLE = "house"
const val H_KEY_ID = "_id"
const val H_KEY_ADDRESS = "address"
const val H_KEY_NUMBER_OF_ENTRANCES = "number_of_entrances"
const val H_KEY_NUMBER_OF_FLOORS = "number_of_floors"
const val H_KEY_MANAGEMENT_COMPANY = "management_company"
const val H_KEY_STREET_ID = "street_id"

const val ASSETS_TABLE = "assets"
const val A_KEY_ID = "_id"
const val A_KEY_SURNAME = "surname"
const val A_KEY_NAME = "name"
const val A_KEY_PATRONYMIC = "patronymic"
const val A_KEY_APARTMENT_NUMBER = "apartment_number"
const val A_KEY_PHONE_NUMBER = "phone_number"
const val A_KEY_MAIL = "mail"
const val A_KEY_HOUSE_ID = "house_id"

const val PHOTO_TABLE = "photo_archive"
const val P_KEY_ID = "_id"
const val P_KEY_FILEPATH = "filepath"
const val P_KEY_HOUSE_ID = "house_id"

const val WORK_TABLE = "work_table"
const val W_KEY_ID = "_id"
const val W_KEY_NAME = "work_name"
const val W_KEY_DATE = "work_date"
const val W_KEY_STATE = "work_state"
const val W_KEY_HOUSE_ID = "house_id"

class DBHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        structureInit(db)
        debugInsert(db)
    }

    private fun debugInsert(db: SQLiteDatabase?) {
        if (db != null) {
            var contentValues = listOf(
                ContentValues().apply {
                    put(R_KEY_NAME, "Авиастроительный")
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Вахитовский")
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Кировский")
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Ново-Совиновский")
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Приволжский")
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Советский")
                }
            )
            contentValues.forEach {
                db?.insert(REGION_TABLE, null, it)
            }
            contentValues = listOf(
                ContentValues().apply {
                    put(D_KEY_NAME, "Северный одномандатный избирательный округ №1")
                    put(D_KEY_REGION_ID,1)
                }
            )
            contentValues.forEach {
                db?.insert(DISTINCT_TABLE, null, it)
            }
        }
    }

    private fun structureInit(db: SQLiteDatabase?) {
        //регионы
        db?.execSQL(
            "create table " + REGION_TABLE + "(" + R_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + R_KEY_NAME + " text" + ")"
        )
        //округа
        db?.execSQL(
            "create table " + DISTINCT_TABLE + "(" + D_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + D_KEY_NAME + " text," + D_KEY_REGION_ID
                    + " INTEGER NOT NULL, " + "FOREIGN KEY (" + D_KEY_REGION_ID + ") REFERENCES "
                    + REGION_TABLE + "(" + R_KEY_ID + ")" + ")"
        )
        //улицы
        db?.execSQL(
            "create table " + STREET_TABLE + "(" + S_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + S_KEY_NAME + " text, " + S_KEY_DISTINCT_ID
                    + " INTEGER NOT NULL, " + "FOREIGN KEY (" + S_KEY_DISTINCT_ID + ") REFERENCES "
                    + DISTINCT_TABLE + "(" + D_KEY_ID + ")" + ")"
        )
        //дома
        db?.execSQL(
            "create table " + HOUSE_TABLE + "(" + H_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + H_KEY_ADDRESS + " text," + H_KEY_NUMBER_OF_ENTRANCES
                    + " integer," + H_KEY_NUMBER_OF_FLOORS + " integer, " + H_KEY_MANAGEMENT_COMPANY + " text, "
                    + H_KEY_STREET_ID + " INTEGER NOT NULL, " + "FOREIGN KEY (" + H_KEY_STREET_ID + ") REFERENCES "
                    + STREET_TABLE + "(" + S_KEY_ID + ")" + ")"
        )
        //фото
        db?.execSQL(
            "create table " + PHOTO_TABLE + "(" + P_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + P_KEY_FILEPATH + " text," + P_KEY_HOUSE_ID
                    + " INTEGER NOT NULL, " + "FOREIGN KEY (" + P_KEY_HOUSE_ID + ") REFERENCES "
                    + HOUSE_TABLE + "(" + H_KEY_ID + ")" + ")"
        )
        //актив и сторонники
        db?.execSQL(
            "create table " + ASSETS_TABLE + "(" + A_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + A_KEY_SURNAME + " text," + A_KEY_NAME
                    + " text," + A_KEY_PATRONYMIC + " text, " + A_KEY_APARTMENT_NUMBER + " integer, "
                    + A_KEY_PHONE_NUMBER + " text, " + A_KEY_MAIL + " text, "
                    + A_KEY_HOUSE_ID + " INTEGER NOT NULL, " + "FOREIGN KEY (" + A_KEY_HOUSE_ID + ") " +
                    "REFERENCES " + HOUSE_TABLE + "(" + H_KEY_ID + ")" + ")"
        )
        //работы
        db?.execSQL(
            "create table " + WORK_TABLE + "(" + W_KEY_ID
                    + " integer primary key AUTOINCREMENT, " + W_KEY_NAME + " text," + W_KEY_DATE
                    + " text," + W_KEY_STATE + " integer, "
                    + W_KEY_HOUSE_ID + " INTEGER NOT NULL, " + "FOREIGN KEY (" + W_KEY_HOUSE_ID + ") " +
                    "REFERENCES " + HOUSE_TABLE + "(" + H_KEY_ID + ")" + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $REGION_TABLE");
        db?.execSQL("drop table if exists $DISTINCT_TABLE");
        db?.execSQL("drop table if exists $STREET_TABLE");
        db?.execSQL("drop table if exists $HOUSE_TABLE");
        db?.execSQL("drop table if exists $ASSETS_TABLE");
        db?.execSQL("drop table if exists $PHOTO_TABLE");
        db?.execSQL("drop table if exists $WORK_TABLE");
        onCreate(db);
    }
}