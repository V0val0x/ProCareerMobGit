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
fun JobDetailsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Вакансии", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = Color(0xFF3A6EFF),
                modifier = Modifier
                    .padding(WindowInsets.statusBars.asPaddingValues()), // Отступ для верхнего бара
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Возвращаемся назад
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back), // Замените на реальный ресурс иконки "назад"
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFE1E4FD),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Название вакансии и уровень
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Системный аналитик",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A6EFF)
                        )
                        Text(
                            text = "Intern",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color(0xFFFF80AB), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Описание вакансии
                    Text(
                        text = "Fair Pricing (FP) — автоматизированная программа индивидуального ценообразования по продуктам\n\n" +
                                "Обязанности\n" +
                                "— Сбор, анализ требований к системе;\n" +
                                "— Формализация и согласование требований;\n" +
                                "— Передача требований в разработку;\n" +
                                "— Консультация членов команды;\n" +
                                "— Согласование тестовых сценариев;\n" +
                                "— Участие в тестировании разработанных компонентов, при необходимости;\n\n" +
                                "Стек технологий:\n" +
                                "Python 3 (FastAPI, Django, Pandas/Numpy/SciPy), СУБД PostgreSQL, Apache Ignite, Apache Kafka, Hive, Pyspark\n\n" +
                                "Требования:\n" +
                                "— Высшее образование по профилю (математическое, экономическое, техническое);\n" +
                                "— Уверенное знание SQL и Excel;\n" +
                                "— Обязателен опыт составления/описания алгоритмов;",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}