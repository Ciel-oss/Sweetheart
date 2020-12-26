package com.sweetheart.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Retrofit构建器
object ServiceCreator {

    //BASE_URL用于指定Retrofit的根路径
    private const val BASE_URL="https://api.caiyunapp.com/"

    //使用Retrofit.Builder构建一个Retrofit对象
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //创建出相应Service接口的动态代理对象
    fun <T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)

    //inline reified泛型实化的两大前提
    inline fun <reified T> create():T= create(T::class.java)
}