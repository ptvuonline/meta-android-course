package com.drkumo.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drkumo.littlelemon.data.DataStorePersonRepository
import com.drkumo.littlelemon.data.MenuRepository
import com.drkumo.littlelemon.data.MenuRepositoryImpl
import com.drkumo.littlelemon.data.PersonRepository
import com.drkumo.littlelemon.model.Person
import com.drkumo.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

class MainActivity : ComponentActivity() {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                MyNavigation(client)
            }
        }
    }
}


@Composable
fun MyNavigation(client: HttpClient) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository: PersonRepository = remember { DataStorePersonRepository(context) }
    val menuDao = MyApp.database.menuDao()
    val menuRepository: MenuRepository = remember { MenuRepositoryImpl(client, menuDao) }
    var person: Person? by remember { mutableStateOf(null) }
    var startDestinations by remember {
        mutableStateOf(OnBoardingRoute.route)
    }

    LaunchedEffect(Unit) {
        person = repository.readPerson()
        Log.d("OnBoarding", "person: $person")
        if (person != null) {
            startDestinations = HomeRoute.route
            navController.navigate(HomeRoute.route)
        }
    }

    NavHost(navController = navController, startDestination = OnBoardingRoute.route) {
        composable(OnBoardingRoute.route) {
            OnBoardingScreen(navController = navController, personRepository = repository)
        }
        composable(HomeRoute.route) {
            HomeScreen(navController = navController, menuRepository = menuRepository)
        }
        composable(ProfileRoute.route) {
            ProfileScreen(navController = navController, personRepository = repository)
        }
    }
}