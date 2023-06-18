package com.drkumo.littlelemon

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drkumo.littlelemon.data.PersonRepository
import com.drkumo.littlelemon.model.Person
import com.drkumo.littlelemon.ui.theme.LittleLemonTheme
import com.drkumo.littlelemon.ui.theme.Yellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController?, personRepository: PersonRepository?) {
    val coroutineScope = rememberCoroutineScope()
    var person: Person? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        person = personRepository?.readPerson()
    }

    val onLogoutClick = {
        coroutineScope.launch {
            personRepository?.clearPerson()
            navController?.popBackStack(OnBoardingRoute.route, inclusive = false)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(all = 8.dp),
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


            Box(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 40.dp),
                    text = "Personal information",
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                TextField(modifier = Modifier.fillMaxWidth(), value = person?.firstName ?: "" , onValueChange = {  }, enabled = false, label = { Text(text = "First name") })
                Box(modifier = Modifier.height(16.dp))
                TextField(modifier = Modifier.fillMaxWidth(), value = person?.lastName ?: "", onValueChange = {  }, enabled = false, label = { Text(text = "Last name") })
                Box(modifier = Modifier.height(16.dp))
                TextField(modifier = Modifier.fillMaxWidth(), value = person?.email ?: "", onValueChange = {  }, enabled = false, label = { Text(text = "Email") })
            }

//            Button(onClick = {
//                navController?.popBackStack()
//            }) {
//                Text(text = "Back")
//            }

            Box(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .background(
//                            shape = RoundedCornerShape(8.dp),
//                            color = Yellow
//                        )
//                        .border(width = 1.dp, color = YellowBorder, shape = RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Yellow,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),

                    onClick = { onLogoutClick() }) {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreen(navController = null, personRepository = null)
    }
}