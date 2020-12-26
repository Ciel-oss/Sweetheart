package com.sweetheart.android.logic.dao

import androidx.room.*

// 创建一个操作实体类的dao接口,用@Dao进行注解
@Dao
interface PlaceDao : BaseDao<LocalPlace> {

    @Query("select * from PlaceDao order by time desc limit 5")
    fun getPlaces(): MutableList<LocalPlace>

    //判断是否有数据已被存储
    @Query("select * from PlaceDao where lat = :lat AND lng = :lng")
    fun getPlace(lat: String,lng:String):LocalPlace?

}