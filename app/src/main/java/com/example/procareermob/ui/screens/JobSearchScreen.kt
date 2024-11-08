package com.example.procareermob.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procareermob.R
import com.google.accompanist.flowlayout.FlowRow

data class Job(
    val title: String,
    val level: String,
    val date: String,
    val skills: List<String>
)

@Composable
fun JobSearchScreen(navController: NavController) {
    val jobList = remember {
        listOf(
            Job(
                title = "Системный аналитик",
                level = "Intern",
                date = "Сегодня",
                skills = listOf("Python", "ООП", "BPMN", "PostgreSQL", "SQL")
            ),
            Job(
                title = "Backend-разработчик",
                level = "Intern/Junior",
                date = "25.09.2024",
                skills = listOf("Python", "JS", "PHP")
            ),
            Job(
                title = "Python Developer",
                level = "Junior",
                date = "Сегодня",
                skills = listOf("Python", "SQL", "pandas", "numpy", "ORM")
            ),
            Job(
                title = "Fullstack-разработчик",
                level = "Junior",
                date = "08.09.2024",
                skills = listOf("SQL", "React", "Ruby")
            ),
            Job(
                title = "Fullstack-разработчик",
                level = "Junior",
                date = "08.09.2024",
                skills = listOf("SQL", "React", "Ruby")
            ),
            Job(
                title = "Fullstack-разработчик",
                level = "Junior",
                date = "08.09.2024",
                skills = listOf("SQL", "React", "Ruby")
            ),
            Job(
                title = "Fullstack-разработчик",
                level = "Junior",
                date = "08.09.2024",
                skills = listOf("SQL", "React", "Ruby")
            )
        )
    }

    val lazyListState = rememberLazyListState()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
            Text(
                text = "Вакансии",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3A6EFF),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 108.dp)
            )

            // Поле поиска
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Обработка поиска */ },
                placeholder = { Text(text = "Поиск") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Color(0xFF3A6EFF),
                        modifier = Modifier.size(20.dp) // Устанавливаем размер иконки
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter Icon",
                        tint = Color(0xFF3A6EFF),
                        modifier = Modifier
                            .size(20.dp) // Устанавливаем размер иконки
                            .clickable { /* TODO: Обработка фильтрации */ }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp) // Задаем фиксированную высоту
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF3A6EFF),
                    unfocusedBorderColor = Color.Gray,
                    backgroundColor = Color.White // Белый фон для устранения аномалий
                )
            )

            // Список вакансий
            LazyColumn(
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(jobList) { job ->
                    JobCard(job, onClick = { navController.navigate("job_details") })
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCard(job: Job, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Верхняя строка с названием и датой
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3A6EFF)
                )
                Text(
                    text = job.date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Уровень должности
            Text(
                text = job.level,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFFFF80AB), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Список навыков
            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                job.skills.forEach { skill ->
                    Text(
                        text = skill,
                        color = Color(0xFF3A6EFF),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}