package com.example.procareermob.network

data class LoginResponse(
    val data: Data,
    val message: String,
    val errors: String?
)

data class Data(
    val token: String,
    val userId: Int
)