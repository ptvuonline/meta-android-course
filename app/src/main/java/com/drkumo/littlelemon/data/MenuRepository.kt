package com.drkumo.littlelemon.data

import com.drkumo.littlelemon.model.MenuItemNetwork

interface MenuRepository {
    suspend fun readMenu(): List<MenuItemNetwork>
}