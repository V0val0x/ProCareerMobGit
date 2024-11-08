package com.example.procareermob.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Test(val title: String, val duration: String, val color: Color)

@Composable
fun TestListScreen(navController: NavController) {
    val testList = listOf(
        Test("Тест на знание SQL", "Время прохождения ≈ 45 мин", Color(0xFFFF80AB)),
        Test("Тест на знание Python", "Время прохождения ≈ 1 час", Color(0xFF66D2A5)),
        Test("Тест на знание Jira", "Время прохождения ≈ 45 мин", Color(0xFFFF80AB)),
        Test("Тест на знание C#", "Время прохождения ≈ 45 мин", Color(0xFFFF80AB))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тестирование", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = Color(0xFF3A6EFF),
                modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(testList) { test ->
                TestCard(test, onClick = { navController.navigate("test_details") })
            }
        }
    }
}

@Composable
fun TestCard(test: Test, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = test.color,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = test.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = test.duration,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF))
            ) {
                Text(text = "Начать", color = Color.White)
            }
        }
    }
}