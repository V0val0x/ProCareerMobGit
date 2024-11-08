package com.example.procareermob.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procareermob.R

@Composable
fun TestDetailsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TestDetailsTopBar(navController)
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        TestDetailsContent(modifier = Modifier.padding(padding))
    }
}

@Composable
fun TestDetailsTopBar(navController: NavController) {
    TopAppBar(
        title = { Text("Тестирование", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        backgroundColor = Color(0xFF3A6EFF),
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back), // Замените на реальный ресурс иконки "назад"
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun TestDetailsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xFF66D2A5),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Тест на уровень знаний SQL",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Время прохождения ≈ 45 мин",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Начать тест */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF))
                ) {
                    Text(text = "Начать", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Помните, что мы не школа, чтобы ругать вас за ошибки. Этот тест скорее для того, чтобы понять ваши пробелы. Что-то не знать – нестрашно. Страшно думать, что ты все знаешь..",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}