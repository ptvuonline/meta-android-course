package com.drkumo.littlelemon.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkData(
    val menu: List<MenuItemNetwork>
)