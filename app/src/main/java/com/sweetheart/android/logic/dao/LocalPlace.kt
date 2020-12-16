package com.sweetheart.android.logic.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PlaceDao")
data class LocalPlace(

    @ColumnInfo(name = "time")
    var time: Long?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "address")
    var address: String?,
    @ColumnInfo(name = "lng")
    var lng: String?,
    @ColumnInfo(name = "lat")
    var lat: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null
    override fun toString(): String {
        return "LocalPlace(id=$id, time=$time, name=$name, address=$address, lng=$lng, lat=$lat)"
    }
}