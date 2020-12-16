package com.sweetheart.android.logic

import androidx.lifecycle.liveData
import com.sweetheart.android.logic.dao.AppDataBase
import com.sweetheart.android.logic.dao.LocalPlace
import com.sweetheart.android.logic.dao.PlaceDao
import com.sweetheart.android.logic.model.Place
import com.sweetheart.android.logic.model.Weather
import com.sweetheart.android.logic.network.SweetHeartNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import kotlin.coroutines.CoroutineContext

object Repository {

  var mPlaceDao=  AppDataBase.instance.getPlaceDao();
    fun searchPlaces(query:String)= fire(Dispatchers.IO){

        val placeResponse=SweetHeartNetwork.searchPlaces(query)
        if (placeResponse.status=="ok"){
            val places=placeResponse.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }

    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO) {

        coroutineScope {
            val deferredRealtime=async {
                SweetHeartNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily=async {
                SweetHeartNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse=deferredRealtime.await()
            val dailyResponse=deferredDaily.await()
            if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                val weather=Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(
                        RuntimeException(
                                "realtime response status is ${realtimeResponse.status}"+
                                        "daily response status is ${dailyResponse.status}"
                        )
                )
            }
        }

    }

    private fun <T> fire(context:CoroutineContext, block:suspend () -> Result<T>) = liveData<Result<T>>(context) {
        val result=try {
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun savePlace(place: Place) {
        var localPlace = mPlaceDao.getPlace(place.location.lat, place.location.lng)
        if (localPlace != null) {
            localPlace.time = Date().time
            mPlaceDao.update(localPlace)
        } else {
            localPlace = LocalPlace(
                Date().time,
                place.name,
                place.address,
                place.location.lng,
                place.location.lat
            )
            mPlaceDao.insert(localPlace)
        }
    }
    fun savePlace(place: LocalPlace) {
        var localPlace = mPlaceDao.getPlace(place.lat!!, place.lng!!)
        if (localPlace != null) {
            localPlace.time = Date().time
            mPlaceDao.update(localPlace)
        } else {
            localPlace = LocalPlace(
                Date().time,
                place.name,
                place.address,
                place.lng,
                place.lat
            )
            mPlaceDao.insert(localPlace)
        }
    }

    fun getSavedPlaces() = mPlaceDao.getPlaces()
    fun deletePlace(place: LocalPlace)= mPlaceDao.delete(place)



}