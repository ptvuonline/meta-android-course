package com.drkumo.littlelemon

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.drkumo.littlelemon.data.MenuRepository
import com.drkumo.littlelemon.model.MenuItemNetwork
import com.drkumo.littlelemon.ui.theme.Green
import com.drkumo.littlelemon.ui.theme.GreenBackground
import com.drkumo.littlelemon.ui.theme.GreenText
import com.drkumo.littlelemon.ui.theme.KarlaFont
import com.drkumo.littlelemon.ui.theme.LittleLemonTheme
import com.drkumo.littlelemon.ui.theme.MarkaziFont
import com.drkumo.littlelemon.ui.theme.Yellow
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController?, menuRepository: MenuRepository?) {
    var menuItems: List<MenuItemNetwork> by remember { mutableStateOf(listOf()) }
    var categories: List<String> by remember { mutableStateOf(listOf()) }
    var searchPhrase: String by remember { mutableStateOf("") }
    var selectedCategory: String by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val response = menuRepository?.readMenu()
        Log.d("HomeScreen", "$response")
        response?.let { items ->
            val categoriesTemp = mutableSetOf<String>()
            items.forEach { menu -> categoriesTemp.add(menu.category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }) }
            categories = categoriesTemp.toList()
            menuItems = items
        }
    }

    var filterItems: List<MenuItemNetwork> by remember { mutableStateOf(listOf()) }
    LaunchedEffect(key1 = searchPhrase, key2 = menuItems, key3 = selectedCategory) {
        filterItems = menuItems.filter { item ->
            if (searchPhrase.isBlank()) {
                return@filter true
            } else return@filter item.title.contains(searchPhrase, ignoreCase = true)
        }
            .filter {item ->
                if (selectedCategory.isBlank()) {
                    return@filter true
                } else return@filter item.category.endsWith(selectedCategory, ignoreCase = true)
            }
            .sortedBy { it.title }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(1f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.width(60.dp))
                Image(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .height(80.dp)
                        .padding(vertical = 24.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    modifier = Modifier
                        .width(60.dp)
                        .clickable { navController?.navigate(ProfileRoute.route) },
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "profile",
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(
                modifier = Modifier
                    .background(color = Green)
                    .padding(all = 16.dp)

            ) {
                Text(
                    text = "Little Lemon",
                    color = Yellow,
                    fontSize = 52.sp,
                    fontFamily = MarkaziFont,
                    fontWeight = FontWeight.SemiBold,
                )
                Row {
                    Column(
                        modifier = Modifier.weight(1f, true)
                    ) {
                        Text(
                            text = "Chicago",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = MarkaziFont
                        )
                        Text(
                            text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = KarlaFont
                        )
                    }
                    Box(modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.hero_image),
                            contentDescription = "profile",
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }

                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Box {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = searchPhrase,
                            onValueChange = { searchPhrase = it },
                            placeholder = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Enter search phrase",
                                        fontFamily = KarlaFont,
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "") }
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Box(modifier = Modifier.height(height = 16.dp))
                Text(
                    text = "ORDER FOR DELIVERY!",
                    fontSize = 20.sp,
                    fontFamily = KarlaFont,
                    fontWeight = FontWeight.Bold,
                )
                Box(modifier = Modifier.height(height = 8.dp))
                LazyRow {
                    items(categories) { item ->
                        CategoryItem(
                            item,
                            isSelected = item.equals(selectedCategory, ignoreCase = true),
                            onPress = {
                                selectedCategory = if (selectedCategory.equals(item, ignoreCase = true)) {
                                    ""
                                } else {
                                    item
                                }
                            }
                        )
                    }
                }
                Box(modifier = Modifier.height(height = 16.dp))
                Divider()
            }

            LazyColumn(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                items(filterItems) { item -> DishItemView(item) }
            }
        }
    }
}

@Composable
fun CategoryItem(item: String, isSelected: Boolean = false, onPress: () -> Unit) {
    Row {
        Box(
            modifier = Modifier
                .background(if (isSelected) Green else GreenBackground, RoundedCornerShape(20.dp))
                .clickable { onPress() },
        ) {
            Text(
                text = item,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                color = if (isSelected) Color.White else GreenText,
                fontWeight = FontWeight.Bold,
            )
        }
        Box(modifier = Modifier.padding(end = 8.dp))
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(item = "Salads", onPress = {})
}

@Composable
fun DishItemView(item: MenuItemNetwork) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.clickable {
            Toast.makeText(context, "${item.title} clicked", Toast.LENGTH_SHORT).show()
        }
    ) {
        Text(
            text = item.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = KarlaFont,
            fontSize = 16.sp,
            maxLines = 1
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f, true)) {
                Text(
                    text = item.description,
                    color = GreenText,
                    fontFamily = KarlaFont,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = item.price.formatPrice(),
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp,
                    color = GreenText,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Box(
                modifier = Modifier.size(80.dp)
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = item.title,
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Preview
@Composable
fun DishItemViewPreview() {
    LittleLemonTheme {
        DishItemView(
            item = MenuItemNetwork(
                price = "10",
                image = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                category = "Salads",
                description = "Our salad is mad from grilled bread that has been smeared with garlic and so on rilled bread that has been smeared with garlic and so on",
                id = 1,
                title = "Greek Salad"
            )
        )
    }
}

private fun String.formatPrice(): String {
    val parsedValue: Double = this.toDoubleOrNull() ?: 0.0
    return "$${String.format("%.2f", parsedValue)}"
}

@Preview
@Composable
fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreen(null, null)
    }
}