package com.sweetheart.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import androidx.room.*
import com.google.gson.Gson
import com.sweetheart.android.SweetHeartApplication
import com.sweetheart.android.logic.model.Place


@Dao
interface PlaceDao : BaseDao<LocalPlace> {

    @Query("select * from PlaceDao order by time desc  limit 6")
    fun getPlaces(): MutableList<LocalPlace>

    //判断是否有数据已被存储
    @Query("select * from PlaceDao where lat = :lat AND lng = :lng")
    fun getPlace(lat: String,lng:String):LocalPlace?

    private fun sharedPreferences() =
        SweetHeartApplication.context.getSharedPreferences("sweet_heart", Context.MODE_PRIVATE)

}