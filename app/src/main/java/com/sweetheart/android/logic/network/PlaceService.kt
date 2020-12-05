package com.sweetheart.android.logic.network

import com.sweetheart.android.SweetHeartApplication
import com.sweetheart.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//访问彩云天气城市搜索API的Retrofit接口
interface PlaceService {
    @GET("v2/place?token=${SweetHeartApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>
}