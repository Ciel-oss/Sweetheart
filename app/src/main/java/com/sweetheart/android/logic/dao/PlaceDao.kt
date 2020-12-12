package com.sweetheart.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sweetheart.android.SweetHeartApplication
import com.sweetheart.android.logic.model.Place

object PlaceDao {

    //通过GSON将Place对象转成一个JSON字符串，然后用字符串存储的方式来保存数据
    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    //将JSON字符串从SharedPreferences文件中读取出来，然后再通过GSON将JSON字符串解析成Place对象并返回
    fun getSavedPlace(): Place{
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    //判断是否有数据已被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SweetHeartApplication.context.getSharedPreferences("sweet_heart",Context.MODE_PRIVATE)

}