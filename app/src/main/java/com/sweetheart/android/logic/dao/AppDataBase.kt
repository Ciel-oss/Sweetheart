package com.sweetheart.android.logic.dao

import androidx.room.*
import com.sweetheart.android.SweetHeartApplication

@Database(entities = [LocalPlace::class],version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getPlaceDao():PlaceDao

    companion object{
        val instance = Single.sin
    }

    private object Single{
        val sin: AppDataBase = Room.databaseBuilder(
            SweetHeartApplication.instance(),
            AppDataBase::class.java,
            "PlaceDB.db"
        )
            .allowMainThreadQueries()
            .build()
    }

}
