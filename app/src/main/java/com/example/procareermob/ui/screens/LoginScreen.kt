package com.example.procareermob.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procareermob.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !it.contains("@")
            },
            label = { Text("Email") },
            isError = emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .drawBehind {
                    // Рисуем тень справа и снизу
                    val shadowColor = Color(0xFFE0E0E0)
                    val radius = 10.dp.toPx()
                    val dx = 5.dp.toPx()
                    val dy = 5.dp.toPx()
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(dx, dy),
                        size = size.copy(width = size.width - dx, height = size.height - dy),
                        alpha = 0.5f,
                    )
                },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email), // Путь к иконке email
                    contentDescription = "Email Icon"
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length <= 4
            },
            label = { Text("Пароль") },
            isError = passwordError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .drawBehind {
                    // Рисуем тень справа и снизу
                    val shadowColor = Color(0xFFE0E0E0)
                    val radius = 10.dp.toPx()
                    val dx = 5.dp.toPx()
                    val dy = 5.dp.toPx()
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(dx, dy),
                        size = size.copy(width = size.width - dx, height = size.height - dy),
                        alpha = 0.5f,
                    )
                },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password), // Путь к иконке пароля
                    contentDescription = "Password Icon"
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visibility), // Путь к иконке видимости пароля
                    contentDescription = "Toggle Password Visibility",
                    tint = Color(0xFFFF4081),
                    modifier = Modifier.clickable {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Забыли пароль?",
            color = Color.Magenta,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { /* Логика входа */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF))
        ) {
            Text("Войти", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}