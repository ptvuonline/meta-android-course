package com.drkumo.littlelemon

import android.app.Application
import androidx.room.Room
import com.drkumo.littlelemon.data.AppDatabase

class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, name = "little-lemon.db").build()
        super.onCreate()
    }


}