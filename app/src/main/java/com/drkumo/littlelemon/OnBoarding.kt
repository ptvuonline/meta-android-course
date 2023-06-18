package com.drkumo.littlelemon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drkumo.littlelemon.data.PersonRepository
import com.drkumo.littlelemon.model.Person
import com.drkumo.littlelemon.ui.theme.Green
import com.drkumo.littlelemon.ui.theme.LittleLemonTheme
import com.drkumo.littlelemon.ui.theme.Yellow
import com.drkumo.littlelemon.ui.theme.YellowBorder
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(navController: NavController, personRepository: PersonRepository) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            Header()
            Content(navController, personRepository)
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .height(100.dp)
                .padding(vertical = 24.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.FillHeight
        )
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(color = Green),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Let's get to know you",
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    LittleLemonTheme {
        Header()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(navController: NavController?, personRepository: PersonRepository?) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onRegisterClick = {
        coroutineScope.launch {
            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || !isEmailValid(email)) {
                Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_LONG).show()
            } else {
                val person = Person(firstName = firstName, lastName = lastName, email = email)
                personRepository?.savePerson(person)
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                navController?.navigate(HomeRoute.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 40.dp),
            text = "Personal information",
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.weight(1f)) {
            TextField(modifier = Modifier.fillMaxWidth(), value = firstName, onValueChange = { firstName = it }, label = { Text(text = "First name") })
            Box(modifier = Modifier.height(16.dp))
            TextField(modifier = Modifier.fillMaxWidth(), value = lastName, onValueChange = { lastName = it }, label = { Text(text = "Last name") })
            Box(modifier = Modifier.height(16.dp))
            TextField(modifier = Modifier.fillMaxWidth(), value = email, onValueChange = { email = it }, label = { Text(text = "Email") })
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Yellow
                    )
                    .border(width = 1.dp, color = YellowBorder, shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Color.Black
                ),
                onClick = { onRegisterClick() }) {
                Text(text = "Register")
            }
        }

    }
}

fun isEmailValid(email: String): Boolean {
    val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(pattern.toRegex())
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    LittleLemonTheme {
        Content(navController = null, personRepository = null)
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LittleLemonTheme {
        Greeting2("Android")
    }
}