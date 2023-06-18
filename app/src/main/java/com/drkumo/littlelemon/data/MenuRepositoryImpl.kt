package com.drkumo.littlelemon.data

import com.drkumo.littlelemon.model.MenuItemNetwork
import com.drkumo.littlelemon.model.MenuNetworkData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MenuRepositoryImpl(private val httpClient: HttpClient, private val menuDao: MenuDao) : MenuRepository {
    override suspend fun readMenu(): List<MenuItemNetwork> {
        var items = menuDao.getMenuItems()
        if (items.isEmpty()) {
            val menu: MenuNetworkData = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body()
            items = menu.menu
            menuDao.insertMenuItems(items)
        }

        return items

    }
}