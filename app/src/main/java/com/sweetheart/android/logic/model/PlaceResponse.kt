package com.sweetheart.android.logic.model

import com.google.gson.annotations.SerializedName

//本文件定义的类与属性按照搜索城市数据接口返回的JSON格式来定义
data class PlaceResponse(val status:String,val places: List<Place>)

data class Place(val name :String,val location:Location,
                 @SerializedName("formatted_address") val address: String)
//JSON与Kotlin的某些命名规范不太一致，用@SerializedName注解让它们建立映射关系

data class Location(val lng:String,val lat:String)