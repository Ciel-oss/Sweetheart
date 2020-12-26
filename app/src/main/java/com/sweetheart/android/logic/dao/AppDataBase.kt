package com.sweetheart.android.logic.dao

import androidx.room.*
import com.sweetheart.android.SweetHeartApplication

//创建一个抽象类，添加@Database注解
@Database(entities = [LocalPlace::class],version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getPlaceDao():PlaceDao

    companion object{
        val instance = Single.sin
    }

    //创建RoomDatabase实例，在application中初始化
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
