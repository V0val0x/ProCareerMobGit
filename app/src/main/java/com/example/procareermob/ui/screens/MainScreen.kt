package com.example.procareermob.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.procareermob.R

@Composable
fun MainScreen(navController: NavController) {
    val items = listOf(
        R.drawable.ic_jobs,
        R.drawable.ic_roadmap,
        R.drawable.ic_tests
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            itemsIndexed(items) { index, imageRes ->
                val modifier = when (index) {
                    0 -> Modifier
                        .padding(start = 0.dp, top = 40.dp, bottom = 16.dp) // Первый элемент
                    items.lastIndex -> Modifier
                        .padding(start = 0.dp, top = 16.dp, bottom = 40.dp) // Последний элемент
                    else -> Modifier.padding(0.dp) // Для остальных элементов
                }

                MainCard(
                    imageRes = imageRes,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp), // Устанавливаем высоту для каждой карточки
                    onClick = {
                        if (imageRes == R.drawable.ic_jobs) {
                            // Переход на страницу вакансий
                            navController.navigate("job_search")
                        }
                        else if (imageRes == R.drawable.ic_tests){
                            navController.navigate("test_list")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MainCard(imageRes: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        // Используем Image с Modifier.fillMaxSize() чтобы изображение занимало всю карточку
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize() // Изображение занимает весь размер карточки
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color(0xFF3A6EFF),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Главная") },
            label = { Text("Главная") },
            selected = true,
            onClick = { navController.navigate("main") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Заметки") },
            label = { Text("Заметки") },
            selected = false,
            onClick = { /* Обработка перехода на заметки */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Поиск") },
            label = { Text("Поиск") },
            selected = false,
            onClick = { /* Обработка перехода на поиск */ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Профиль") },
            label = { Text("Профиль") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
    }
}