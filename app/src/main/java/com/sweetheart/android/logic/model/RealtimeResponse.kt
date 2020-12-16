package com.sweetheart.android.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse (val status: String,val result: Result){

    data class Result(val realtime:Realtime)

    data class Realtime(val skycon:String,val temperature:Float,val apparent_temperature: Float,
                        @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI, val description: Description)

    data class AQI(val chn:Float)

    data class Description(val chn: String)
}