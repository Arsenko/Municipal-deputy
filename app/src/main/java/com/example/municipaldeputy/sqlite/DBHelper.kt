package com.example.municipaldeputy.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.municipaldeputy.R
import com.example.municipaldeputy.constants.*


class DBHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        structureInit(db)
        debugInsert(db)
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
                    + " integer primary key AUTOINCREMENT, " + P_KEY_FILEPATH + " text,"
                    + P_KEY_IS_RESOURCE + " integer, " + P_KEY_HOUSE_ID
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
                    + " integer primary key AUTOINCREMENT, " + W_KEY_NAME + " text," + W_KEY_WORKER +
                    " text," + W_KEY_DATE + " text," + W_KEY_STATE + " integer, "
                    + W_KEY_HOUSE_ID + " INTEGER NOT NULL, " + "FOREIGN KEY (" + W_KEY_HOUSE_ID + ") " +
                    "REFERENCES " + HOUSE_TABLE + "(" + H_KEY_ID + ")" + ")"
        )
    }

    private fun debugInsert(db: SQLiteDatabase?) {
        if (db != null) {

            //районы
            var contentValues = listOf(
                ContentValues().apply {
                    put(R_KEY_NAME, "Авиастроительный")//1
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Вахитовский")//2
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Кировский")//3
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Ново-Совиновский")//4
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Приволжский")//5
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Советский")//6
                },
                ContentValues().apply {
                    put(R_KEY_NAME, "Московский")//7
                }
            )
            contentValues.forEach {
                db?.insert(REGION_TABLE, null, it)
            }

            //округа
            contentValues = listOf(
                ContentValues().apply {
                    put(D_KEY_NAME, "Северный одномандатный избирательный округ №1")//1
                    put(D_KEY_REGION_ID, 1)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Караваевский одномандатный избирательный округ №2")//2
                    put(D_KEY_REGION_ID, 1)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Академический одномандатный избирательный округ №3")//3
                    put(D_KEY_REGION_ID, 2)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Кремлевский одномандатный избирательный округ №4")//4
                    put(D_KEY_REGION_ID, 2)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Пороховой одномандатный избирательный округ №5")//5
                    put(D_KEY_REGION_ID, 3)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Прибрежный одномандатный избирательный округ №6")//6
                    put(D_KEY_REGION_ID, 3)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Декабристский одномандатный избирательный округ №7")//7
                    put(D_KEY_REGION_ID, 7)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Гагаринский одномандатный избирательный округ №8")//8
                    put(D_KEY_REGION_ID, 7)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Тасмовский одномандатный избирательный округ №9")//9
                    put(D_KEY_REGION_ID, 7)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Октябрьский одномандатный избирательный округ №10")//10
                    put(D_KEY_REGION_ID, 4)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Амирхановский одномандатный избирательный округ №11")//11
                    put(D_KEY_REGION_ID, 4)
                },
                ContentValues().apply {
                    put(D_KEY_NAME, "Чуйковский одномандатный избирательный округ №12")//12
                    put(D_KEY_REGION_ID, 4)
                },
            )

            contentValues.forEach {
                db?.insert(DISTINCT_TABLE, null, it)
            }

            //улицы
            contentValues = listOf(
                ContentValues().apply {
                    put(S_KEY_NAME, "Гудовцева")//1
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Камчатская")//2
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Керченская")//3
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Колхозная")//4
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Колхозная 2-я")//5
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Лянгузова")//6
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Малая Заречная")//7
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Ново-Караваевская")//8
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Односторонняя Луговая")//9
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Песочная")//10
                    put(S_KEY_DISTINCT_ID, 1)
                },
                ContentValues().apply {
                    put(S_KEY_NAME, "Петра Баранова")//11
                    put(S_KEY_DISTINCT_ID, 1)
                }
            )
            contentValues.forEach {
                db?.insert(STREET_TABLE, null, it)
            }

            //дома, информация ошибочна, начиная отсюда
            contentValues = listOf(
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Гудовцева №1")//1
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Пжкх\"")
                    put(H_KEY_STREET_ID, 1)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Гудовцева №2")//2
                    put(H_KEY_NUMBER_OF_ENTRANCES, 4)
                    put(H_KEY_NUMBER_OF_FLOORS, 9)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Пжкх\"")
                    put(H_KEY_STREET_ID, 1)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Гудовцева №2a")//3
                    put(H_KEY_NUMBER_OF_ENTRANCES, 2)
                    put(H_KEY_NUMBER_OF_FLOORS, 10)
                    put(H_KEY_MANAGEMENT_COMPANY, "ТСЖ \"Гудок\"")
                    put(H_KEY_STREET_ID, 1)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Камчатская №1")//4
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Жкх Танкодром\"")
                    put(H_KEY_STREET_ID, 2)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Камчатская №2")//5
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Свобода\"")
                    put(H_KEY_STREET_ID, 2)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Камчатская №3")//6
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Пжкх\"")
                    put(H_KEY_STREET_ID, 2)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Керченская №1")//7
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "ТСЖ \"Рога и копыта\"")
                    put(H_KEY_STREET_ID, 3)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Керченская №2")//8
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Жкх Танкодром\"")
                    put(H_KEY_STREET_ID, 3)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Керченская №3")//9
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "Ук \"Жкх Танкодром\"")
                    put(H_KEY_STREET_ID, 3)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Керченская №3а")//10
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "ТСЖ \"Воля народа\"")
                    put(H_KEY_STREET_ID, 3)
                },
                ContentValues().apply {
                    put(H_KEY_ADDRESS, "улица Колхозная №2а")//11
                    put(H_KEY_NUMBER_OF_ENTRANCES, 3)
                    put(H_KEY_NUMBER_OF_FLOORS, 5)
                    put(H_KEY_MANAGEMENT_COMPANY, "ТСЖ \"Воля народа\"")
                    put(H_KEY_STREET_ID, 4)
                }
            )
            contentValues.forEach {
                db?.insert(HOUSE_TABLE, null, it)
            }

            //фото
            contentValues = listOf(
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_1")//1
                    put(P_KEY_HOUSE_ID, 1)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_2")//2
                    put(P_KEY_HOUSE_ID, 1)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_3")//3
                    put(P_KEY_HOUSE_ID, 1)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_4")//4
                    put(P_KEY_HOUSE_ID, 2)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_5")//5
                    put(P_KEY_HOUSE_ID, 2)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_6")//6
                    put(P_KEY_HOUSE_ID, 2)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_1")//7
                    put(P_KEY_HOUSE_ID, 3)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_3")//8
                    put(P_KEY_HOUSE_ID, 3)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_5")//9
                    put(P_KEY_HOUSE_ID, 3)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_2")//10
                    put(P_KEY_HOUSE_ID, 4)
                    put(P_KEY_IS_RESOURCE, 1)
                },
                ContentValues().apply {
                    put(P_KEY_FILEPATH, "house_4")//11
                    put(P_KEY_HOUSE_ID, 4)
                    put(P_KEY_IS_RESOURCE, 1)
                }
            )
            contentValues.forEach {
                db?.insert(PHOTO_TABLE, null, it)
            }

            //сторонники
            contentValues = listOf(
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Мангушева")
                    put(A_KEY_NAME, "Алина")
                    put(A_KEY_PATRONYMIC, "Раисовна")
                    put(A_KEY_APARTMENT_NUMBER, 42)
                    put(A_KEY_PHONE_NUMBER, "+791443582142")
                    put(A_KEY_MAIL, "mangusheva@kstu.ru")
                    put(A_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Герасимов")
                    put(A_KEY_NAME, "Александр")
                    put(A_KEY_PATRONYMIC, "Викторович")
                    put(A_KEY_APARTMENT_NUMBER, 44)
                    put(A_KEY_PHONE_NUMBER, "+797747882142")
                    put(A_KEY_MAIL, "sasha_gv@mail.ru")
                    put(A_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Кирпичников")
                    put(A_KEY_NAME, "Александр")
                    put(A_KEY_PATRONYMIC, "Петрович")
                    put(A_KEY_APARTMENT_NUMBER, 67)
                    put(A_KEY_PHONE_NUMBER, "+797743582142")
                    put(A_KEY_MAIL, "A_petrovich@mail.ru")
                    put(A_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Нгуен")
                    put(A_KEY_NAME, "Нан Ан")
                    put(A_KEY_PATRONYMIC, "")
                    put(A_KEY_APARTMENT_NUMBER, 44)
                    put(A_KEY_PHONE_NUMBER, "897743583142")
                    put(A_KEY_MAIL, "nguen_nan_an@gmail.ru")
                    put(A_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Гурьянов")
                    put(A_KEY_NAME, "Владимир")
                    put(A_KEY_PATRONYMIC, "Владимирович")
                    put(A_KEY_APARTMENT_NUMBER, 34)
                    put(A_KEY_PHONE_NUMBER, "893743583142")
                    put(A_KEY_MAIL, "v_v_gurian@mail.ru")
                    put(A_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Люшиковский")
                    put(A_KEY_NAME, "Петр")
                    put(A_KEY_PATRONYMIC, "Доминович")
                    put(A_KEY_APARTMENT_NUMBER, 12)
                    put(A_KEY_PHONE_NUMBER, "892743585142")
                    put(A_KEY_MAIL, "petr_l89@gmail.com")
                    put(A_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Абануб")
                    put(A_KEY_NAME, "Халаф")
                    put(A_KEY_PATRONYMIC, "Манур")
                    put(A_KEY_APARTMENT_NUMBER, 144)
                    put(A_KEY_PHONE_NUMBER, "+793749275632")
                    put(A_KEY_MAIL, "AHManur@yahoo.com")
                    put(A_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Ватанабе")
                    put(A_KEY_NAME, "Сато")
                    put(A_KEY_PATRONYMIC, "")
                    put(A_KEY_APARTMENT_NUMBER, 37)
                    put(A_KEY_PHONE_NUMBER, "+712291111234")
                    put(A_KEY_MAIL, "vatabeS64@gmail.com")
                    put(A_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Цинь")
                    put(A_KEY_NAME, "Шихуанди")
                    put(A_KEY_PATRONYMIC, "")
                    put(A_KEY_APARTMENT_NUMBER, 35)
                    put(A_KEY_PHONE_NUMBER, "+711691111234")
                    put(A_KEY_MAIL, "chin_kingdom@china.ch")
                    put(A_KEY_HOUSE_ID, 3)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Кузьмин")
                    put(A_KEY_NAME, "Елисей")
                    put(A_KEY_PATRONYMIC, "Дмитриевич")
                    put(A_KEY_APARTMENT_NUMBER, 52)
                    put(A_KEY_PHONE_NUMBER, "+711112111234")
                    put(A_KEY_MAIL, "e_kuzma@mail.ru")
                    put(A_KEY_HOUSE_ID, 3)
                },
                ContentValues().apply {
                    put(A_KEY_SURNAME, "Куликов")
                    put(A_KEY_NAME, "Назарий")
                    put(A_KEY_PATRONYMIC, "Егорович")
                    put(A_KEY_APARTMENT_NUMBER, 22)
                    put(A_KEY_PHONE_NUMBER, "+711111111234")
                    put(A_KEY_MAIL, "kulikov29@gmail.com")
                    put(A_KEY_HOUSE_ID, 3)
                }
            )
            contentValues.forEach {
                db?.insert(ASSETS_TABLE, null, it)
            }
            contentValues = listOf(
                ContentValues().apply {
                    put(W_KEY_NAME, "Капитальный ремонт")
                    put(W_KEY_DATE, "1970-01-01")
                    put(W_KEY_WORKER, "УК \"Пжкх\"")
                    put(W_KEY_STATE, 1)
                    put(W_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Замена водопроводных труб")
                    put(W_KEY_DATE, "2020-10-02")
                    put(W_KEY_WORKER, "СК БРИЗ")
                    put(W_KEY_STATE, 1)
                    put(W_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Ремонт облицовки")
                    put(W_KEY_DATE, "2017-01-01")
                    put(W_KEY_WORKER, "ГК Профит")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Ремонт пожарной лестницы")
                    put(W_KEY_DATE, "2018-03-15")
                    put(W_KEY_WORKER, "УК \"Пжкх\"")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Установка общедомовых счётчиков")
                    put(W_KEY_DATE, "2025-11-11")
                    put(W_KEY_WORKER, "ГК Профит")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 1)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Капитальный ремонт")
                    put(W_KEY_DATE, "2020-10-01")
                    put(W_KEY_WORKER, "УК \"Пжкх\"")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Ремонт детской площадки")
                    put(W_KEY_DATE, "2001-04-01")
                    put(W_KEY_WORKER, "Унистрой")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Благоустройство двора")
                    put(W_KEY_DATE, "2020-07-26")
                    put(W_KEY_WORKER, "СК БРИЗ")
                    put(W_KEY_STATE, 1)
                    put(W_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Замена фонарных столбов")
                    put(W_KEY_DATE, "2017-08-10")
                    put(W_KEY_WORKER, "СК БРИЗ")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Ремонт дорожного покрытия")
                    put(W_KEY_DATE, "2017-08-01")
                    put(W_KEY_WORKER, "Унистрой")
                    put(W_KEY_STATE, 1)
                    put(W_KEY_HOUSE_ID, 2)
                },
                ContentValues().apply {
                    put(W_KEY_NAME, "Плановый снос здания")
                    put(W_KEY_DATE, "2027-08-01")
                    put(W_KEY_WORKER, "УК \"Пжкх\"")
                    put(W_KEY_STATE, 0)
                    put(W_KEY_HOUSE_ID, 0)
                }
            )
            contentValues.forEach {
                db?.insert(WORK_TABLE, null, it)
            }
        }
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