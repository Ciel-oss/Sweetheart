package com.sweetheart.android.logic

import androidx.lifecycle.liveData
import com.sweetheart.android.logic.model.Place
import com.sweetheart.android.logic.network.SweetHeartNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    fun searchPlaces(query:String)= liveData(Dispatchers.IO){
        val result=try {
            val placeResponse=SweetHeartNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places=placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }
}