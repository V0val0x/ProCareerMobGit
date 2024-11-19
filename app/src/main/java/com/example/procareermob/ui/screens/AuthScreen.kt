package com.example.procareermob.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.platform.LocalContext

@Composable
fun AuthScreen(navController: NavController) {
    var isLoginSelected by remember { mutableStateOf(true) } // Состояние для выбора экрана

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        Spacer(modifier = Modifier.height(100.dp))
        // Логотип
        Text(
            text = "ProКарьеру",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3A6EFF) // Цвет логотипа
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопки переключения "Войти" и "Регистрация"
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Войти",
                modifier = Modifier
                    .clickable {
                        isLoginSelected = true
                    }
                    .padding(8.dp),
                color = if (isLoginSelected) Color(0xFF3A6EFF) else Color.Gray,
                fontSize = 24.sp,
                fontWeight = if (isLoginSelected) FontWeight.Bold else FontWeight.Normal
            )

            Spacer(modifier = Modifier.width(32.dp))

            Text(
                text = "Регистрация",
                modifier = Modifier
                    .clickable {
                        isLoginSelected = false
                    }
                    .padding(8.dp),
                color = if (!isLoginSelected) Color(0xFF3A6EFF) else Color.Gray,
                fontSize = 24.sp,
                fontWeight = if (!isLoginSelected) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Подчеркивание активной кнопки
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 61.dp, end = 96.dp),
            horizontalArrangement = if (isLoginSelected) Arrangement.Start else Arrangement.End
        ) {
//            if(isLoginSelected)
//                Spacer(modifier = Modifier.width(58.dp))
//            else
//                Spacer(modifier = Modifier.width(0.dp))
            Divider(
                color = Color(0xFFFF4081),
                thickness = 3.dp,
                modifier = Modifier.width(100.dp)
            )
        }

        //Spacer(modifier = Modifier.height(32.dp))



        Crossfade(targetState = isLoginSelected) { screen ->
            if (screen) {
                LoginScreen(navController, context)
            } else {
                RegistrationScreen(navController,  context = context)
            }
        }
    }
}
