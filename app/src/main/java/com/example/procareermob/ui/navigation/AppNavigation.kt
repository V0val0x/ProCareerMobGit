package com.example.procareermob.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.procareermob.ui.screens.AuthScreen
import com.example.procareermob.ui.screens.LoginScreen
import com.example.procareermob.ui.screens.RegistrationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") { AuthScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("registration") { RegistrationScreen(navController) }
    }
}