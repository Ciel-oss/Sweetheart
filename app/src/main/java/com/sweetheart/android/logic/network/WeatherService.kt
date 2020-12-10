package com.sweetheart.android.logic.network

import com.sweetheart.android.SweetHeartApplication
import com.sweetheart.android.logic.model.DailyResponse
import com.sweetheart.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    //获取实时的天气信息
    @GET("v2.5/${SweetHeartApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat:String):Call<RealtimeResponse>

    //获取未来的天气信息
    @GET("v2.5/${SweetHeartApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat") lat: String):Call<DailyResponse>
    //@Path注解向请求接口中动态传入经纬度的坐标

}