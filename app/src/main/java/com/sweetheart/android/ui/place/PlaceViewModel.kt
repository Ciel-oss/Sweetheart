package com.sweetheart.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sweetheart.android.logic.Repository
import com.sweetheart.android.logic.dao.LocalPlace
import com.sweetheart.android.logic.model.Place

class PlaceViewModel:ViewModel() {

    //对界面上显示的城市数据进行缓存
    val placeList=ArrayList<Place>()

    private val searchLiveData=MutableLiveData<String>()

    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query:String){
        searchLiveData.value=query
    }

    val localPlaceList=ArrayList<LocalPlace>()

    fun savePlace(place: Place) = Repository.savePlace(place)
    fun savePlace(place: LocalPlace) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlaces()
    fun deletePlace(place: LocalPlace) = Repository.deletePlace(place)


}