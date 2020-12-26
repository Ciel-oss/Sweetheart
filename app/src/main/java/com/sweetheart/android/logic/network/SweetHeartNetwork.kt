package com.sweetheart.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//统一的网络数据源访问入口，对所有网络请求的API进行封装
object SweetHeartNetwork {

    //创建一个PlaceService接口的动态代理对象
    private val placeService=ServiceCreator.create<PlaceService>()

    private val weatherService=ServiceCreator.create(WeatherService::class.java)

    //调用PlaceService接口中searchPlaces()方法，发起搜索城市数据请求
    suspend fun searchPlaces(query:String)= placeService.searchPlaces(query).await()

    suspend fun getRealtimeWeather(lng:String,lat:String)= weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeather(lng:String,lat:String)= weatherService.getDailyWeather(lng, lat).await()

    //书中11.7.3使用协程简化Retrofit回调 未理解
    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if (body!=null){
                        continuation.resume(body)
                    }else{
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

}