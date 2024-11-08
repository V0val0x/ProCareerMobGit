package com.example.procareermob.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.focus.onFocusChanged
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
    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }

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
                    val shadowColor = Color(0xFFE0E0E0)
                    val dx = 5.dp.toPx()
                    val dy = 5.dp.toPx()
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(dx, dy),
                        size = size.copy(width = size.width - dx, height = size.height - dy),
                        alpha = 0.5f,
                    )
                }
                .onFocusChanged { focusState ->
                    emailFocused = focusState.isFocused
                },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                // Анимация для значка при фокусе
                val iconOffset by animateDpAsState(if (emailFocused) (-16).dp else 0.dp)
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Email Icon",
                    modifier = Modifier.offset(x = iconOffset)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        // Текст ошибки для email
        AnimatedVisibility(visible = emailError) {
            Text(
                text = "Неверный email",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length < 5
            },
            label = { Text("Пароль") },
            isError = passwordError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .drawBehind {
                    val shadowColor = Color(0xFFE0E0E0)
                    val dx = 5.dp.toPx()
                    val dy = 5.dp.toPx()
                    drawRect(
                        color = shadowColor,
                        topLeft = Offset(dx, dy),
                        size = size.copy(width = size.width - dx, height = size.height - dy),
                        alpha = 0.5f,
                    )
                }
                .onFocusChanged { focusState ->
                    passwordFocused = focusState.isFocused
                },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                // Анимация для значка при фокусе
                val iconOffset by animateDpAsState(if (passwordFocused) (-16).dp else 0.dp)
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password Icon",
                    modifier = Modifier.offset(x = iconOffset)
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visibility),
                    contentDescription = "Toggle Password Visibility",
                    tint = Color(0xFFFF4081),
                    modifier = Modifier.clickable {
                        isPasswordVisible = !isPasswordVisible
                    }
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        // Текст ошибки для пароля
        AnimatedVisibility(visible = passwordError) {
            Text(
                text = "Пароль должен содержать как минимум 5 символов",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Забыли пароль?",
            color = Color.Magenta,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                // Переход на главный экран
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            },
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