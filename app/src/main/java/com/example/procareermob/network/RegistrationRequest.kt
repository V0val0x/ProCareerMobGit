package com.example.procareermob.network

data class RegistrationRequest(
    val email: String,
    val password: String,
    val username: String  // или другие данные, которые нужны для регистрации
)