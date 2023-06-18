package com.drkumo.littlelemon

interface Destinations {
    val route: String
}

object OnBoardingRoute : Destinations {
    override val route: String = "OnBoarding"
}

object HomeRoute : Destinations {
    override val route: String = "Home"
}


object ProfileRoute : Destinations {
    override val route: String = "Profile"
}