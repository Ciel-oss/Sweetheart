package com.sweetheart.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SweetHeartApplication:Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        const val TOKEN="VUI0Lr9NZGBacJh6"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}