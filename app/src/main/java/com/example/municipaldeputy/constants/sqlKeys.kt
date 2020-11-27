package com.example.municipaldeputy.constants

const val DATABASE_NAME = "city structure"
const val DB_VERSION = 26

const val REGION_TABLE = "region"
const val R_KEY_ID = "id"
const val R_KEY_NAME = "name"

const val DISTRICT_TABLE = "district"
const val D_KEY_ID = "id"
const val D_KEY_NAME = "name"
const val D_KEY_REGION_ID = "region_id"

const val STREET_TABLE = "street"
const val S_KEY_ID = "id"
const val S_KEY_NAME = "name"
const val S_KEY_DISTINCT_ID = "street_id"

const val HOUSE_TABLE = "house"
const val H_KEY_ID = "id"
const val H_KEY_ADDRESS = "address"
const val H_KEY_NUMBER_OF_ENTRANCES = "number_of_entrances"
const val H_KEY_NUMBER_OF_FLOORS = "number_of_floors"
const val H_KEY_MANAGEMENT_COMPANY = "management_company"
const val H_KEY_STREET_ID = "street_id"

const val ASSETS_TABLE = "assets"
const val A_KEY_ID = "id"
const val A_KEY_SURNAME = "surname"
const val A_KEY_NAME = "name"
const val A_KEY_PATRONYMIC = "patronymic"
const val A_KEY_APARTMENT_NUMBER = "apartment_number"
const val A_KEY_PHONE_NUMBER = "phone_number"
const val A_KEY_MAIL = "mail"
const val A_KEY_HOUSE_ID = "house_id"

const val PHOTO_TABLE = "photo_archive"
const val P_KEY_ID = "id"
const val P_KEY_FILEPATH = "filepath"
const val P_KEY_IS_RESOURCE = "is_resource"
const val P_KEY_HOUSE_ID = "house_id"

const val WORK_TABLE = "work_table"
const val W_KEY_ID = "id"
const val W_KEY_NAME = "work_name"
const val W_KEY_DATE = "work_date"
const val W_KEY_WORKER ="worker"
const val W_KEY_STATE = "work_state"
const val W_KEY_HOUSE_ID = "house_id"
