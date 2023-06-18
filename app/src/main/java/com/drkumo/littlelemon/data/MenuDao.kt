package com.drkumo.littlelemon.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drkumo.littlelemon.model.MenuItemNetwork

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_items")
    suspend fun getMenuItems(): List<MenuItemNetwork>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(menuItems: List<MenuItemNetwork>)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAllMenuItems()
}