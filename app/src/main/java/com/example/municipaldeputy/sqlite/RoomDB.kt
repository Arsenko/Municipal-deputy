package com.example.municipaldeputy.sqlite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.municipaldeputy.constants.DATABASE_NAME
import com.example.municipaldeputy.constants.DB_VERSION
import com.example.municipaldeputy.entity.*
import com.example.municipaldeputy.sqlite.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Database(
    entities = [
        Region::class,
        District::class,
        Street::class,
        House::class,
        Work::class,
        Human::class,
        PhotoLink::class
    ],
    version = DB_VERSION, exportSchema = false
)
abstract class RoomDB : RoomDatabase() {
    abstract fun regionDao(): RegionDAO
    abstract fun districtDao(): DistrictDAO
    abstract fun streetDao(): StreetDAO
    abstract fun houseDao(): HouseDAO
    abstract fun photoDao(): PhotoDAO
    abstract fun workDao(): WorkDAO
    abstract fun humanDao(): HumanDAO

    companion object {

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}













