package com.example.procareermob.network

import android.content.Context
import android.content.SharedPreferences

fun saveUserData(userId: Int, token: String, context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("user_id", userId)
    editor.putString("token", token)
    editor.apply()
}

fun getUserData(context: Context): Pair<Int?, String?> {
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("user_id", -1)
    val token = sharedPreferences.getString("token", null)
    return Pair(userId.takeIf { it != -1 }, token)
}