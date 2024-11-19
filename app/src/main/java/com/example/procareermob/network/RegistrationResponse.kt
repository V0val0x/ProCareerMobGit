package com.example.procareermob.network

import com.example.procareermob.network.Data

data class RegistrationResponse(
    val data: Data,
    val message: String,
    val errors: String?
)