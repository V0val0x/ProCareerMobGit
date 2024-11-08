package com.example.procareermob.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procareermob.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color(0xFF3A6EFF),
                actions = {
                    Text(
                        text = "Пропустить",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                navController.navigate("main") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                    )
                },
                modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF3A6EFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                count = 5,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPage(page)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Далее"
            IconButton(
                onClick = {
                    if (pagerState.currentPage < 4) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navController.navigate("main") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Next"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Индикация текущей страницы
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp),
                activeColor = Color.White,
                inactiveColor = Color.Gray
            )
        }
    }
}

@Composable
fun OnboardingPage(page: Int) {
    when (page) {
        0 -> OnboardingContent(
            imageRes = R.drawable.onboarding_1,
            text = "Начни свой понятный карьерный путь вместе с нами"
        )
        1 -> OnboardingContent(
            imageRes = R.drawable.onboarding_2,
            text = "Приложение сформирует твой персонализированный роудмап развития"
        )
        2 -> OnboardingContent(
            imageRes = R.drawable.onboarding_3,
            text = "Поможет понять, какие есть пробелы"
        )
        3 -> OnboardingContent(
            imageRes = R.drawable.onboarding_4,
            text = "Выдаст всевозможные вакансии, чтобы тебе было удобно"
        )
        4 -> OnboardingContent(
            imageRes = R.drawable.onboarding_5,
            text = "Отслеживай свои успехи"
        )
    }
}

@Composable
fun OnboardingContent(imageRes: Int, text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}