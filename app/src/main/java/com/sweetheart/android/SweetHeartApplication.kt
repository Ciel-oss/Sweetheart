package com.sweetheart.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class SweetHeartApplication:Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val TOKEN="VUI0Lr9NZGBacJh6"

        var instance: SweetHeartApplication by Delegates.notNull()

        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        instance=this
    }
}