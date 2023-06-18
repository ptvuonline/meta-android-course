package com.drkumo.littlelemon.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.drkumo.littlelemon.model.MenuItemNetwork

@Database(entities = [MenuItemNetwork::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}