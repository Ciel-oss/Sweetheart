package com.sweetheart.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyResponse(val status:String,val result:Result){

    data class Result(val daily:Daily)

    data class Daily(val temperature:List<Temperature>,val skycon:List<Skycon>,
                     @SerializedName("life_index") val lifeIndex: LifeIndex,
                     val pressure: List<Pressure>, val cloudrate: List<Cloudrate>,
                     val visibility: List<Visibility>, val humidity: List<Humidity>,
                     val precipitation: List<Precipitation>, val dswrf: List<Dswrf>,
                     val astro: List<Astro>)
    //气温
    data class Temperature(val max:Float,val min:Float)
    //天气状况
    data class Skycon(val value:String,val date:Date)
    //生活指数
    data class LifeIndex(val coldRisk:List<LifeDescription>,val carWashing:List<LifeDescription>,
                         val ultraviolet:List<LifeDescription>,val dressing:List<LifeDescription>)

    data class LifeDescription(val desc:String)
    //气压
    data class Pressure(val avg: Float)
    //云量
    data class Cloudrate(val avg: Float)
    //能见度
    data class Visibility(val avg: Float)
    //湿度
    data class Humidity(val avg: Float)
    //降雨量
    data class Precipitation(val avg: Float)
    //短波辐射通量
    data class Dswrf(val avg: Float)
    //日出日落时刻
    data class Astro(val sunrise: Sunrise, val sunset: Sunset)

    data class Sunrise(val time: String)

    data class Sunset(val time: String)
}