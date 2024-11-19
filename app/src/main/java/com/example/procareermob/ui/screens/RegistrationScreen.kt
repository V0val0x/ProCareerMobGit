package com.example.procareermob.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.procareermob.network.RegistrationRequest
import com.example.procareermob.network.RetrofitClient
import com.example.procareermob.network.RegistrationResponse
import com.example.procareermob.network.saveUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun RegistrationScreen(navController: NavController, context: Context) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var registrationError by remember { mutableStateOf<String?>(null) }

    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    // Функция для сохранения данных в SharedPreferences
    fun saveUserData(userId: Int, token: String) {
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", userId)
        editor.putString("token", token)
        editor.apply()
    }

    // Функция для отправки запроса на сервер
    fun registerUser(name: String, email: String, password: String) {
        isLoading = true
        RetrofitClient.api.register(RegistrationRequest(email, password, name)).enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    val registrationResponse = response.body()
                    if (registrationResponse != null) {
                        // Сохранение данных в SharedPreferences
                        val userId = registrationResponse.data.userId
                        val token = registrationResponse.data.token
                        saveUserData(userId, token, context)  // Сохраняем данные

                        // Переход на главный экран
                        navController.navigate("main") {
                            popUpTo("registration") { inclusive = true }
                        }
                    } else {
                        registrationError = "Неверный ответ от сервера"
                    }
                } else {
                    registrationError = "Ошибка регистрации: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                isLoading = false
                registrationError = "Ошибка сети: ${t.message}"
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        // Поле для ввода имени
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text("Имя") },
            isError = nameError,
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
                .onFocusChanged { focusState -> nameError = name.isEmpty() },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                val iconOffset by animateDpAsState(if (name.isNotEmpty()) (-16).dp else 0.dp)
                Icon(
                    painter = painterResource(id = R.drawable.ic_name),
                    contentDescription = "Name Icon",
                    modifier = Modifier.offset(x = iconOffset)
                )
            }
        )

        // Текст ошибки для имени
        AnimatedVisibility(visible = nameError) {
            Text(
                text = "Имя не может быть пустым",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Поле для ввода email
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
                .onFocusChanged { focusState -> emailError = !email.contains("@") },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                val iconOffset by animateDpAsState(if (email.isNotEmpty()) (-16).dp else 0.dp)
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

        Spacer(modifier = Modifier.height(16.dp))

        // Поле для ввода пароля
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
                .onFocusChanged { focusState -> passwordError = password.length <= 4 },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                val iconOffset by animateDpAsState(if (password.isNotEmpty()) (-16).dp else 0.dp)
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && !emailError && !passwordError) {
                    registerUser(name, email, password)
                } else {
                    Toast.makeText(context, "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A6EFF))
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Регистрация", fontSize = 18.sp, color = Color.White)
            }
        }

        // Вывод ошибки при регистрации
        AnimatedVisibility(visible = registrationError != null) {
            Text(
                text = registrationError ?: "",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
