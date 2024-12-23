package com.example.procareermob.ui.navigation


import android.content.Context
import android.content.SharedPreferences
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.procareermob.ui.screens.AuthScreen
import com.example.procareermob.ui.screens.JobSearchScreen
import com.example.procareermob.ui.screens.LoginScreen
import com.example.procareermob.ui.screens.MainScreen
import com.example.procareermob.ui.screens.RegistrationScreen
import com.example.procareermob.ui.screens.JobDetailsScreen
import com.example.procareermob.ui.screens.TestDetailsScreen
import com.example.procareermob.ui.screens.TestListScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.animation.*
import androidx.compose.ui.platform.LocalContext
import com.example.procareermob.network.saveUserData
import com.example.procareermob.network.getUserData
import com.example.procareermob.ui.screens.OnboardingScreen
import com.example.procareermob.ui.screens.ProfileScreen
import com.example.procareermob.ui.theme.SplashScreen

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()

    // Получаем SharedPreferences
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val (userId, token) = getUserData(context)

    // Определяем стартовый экран в зависимости от наличия токена
    val startDestination = if (token != null) "main" else "auth"

        //val context = LocalContext.current

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") { SplashScreen(navController) }
        composable("auth") { AuthScreen(navController) }
        composable("login") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                LoginScreen(navController,  context = context)
            }
        }
        composable("registration") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                RegistrationScreen(navController,  context = context)
            }
        }

        composable("main") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                MainScreen(navController)
            }
        }

        composable("job_search") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                JobSearchScreen(navController)
            }
        }

        composable("job_details") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                JobDetailsScreen(navController = navController)
            }
        }

        composable("test_list") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                TestListScreen(navController = navController)
            }
        }

        composable("test_details") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                TestDetailsScreen(navController = navController)
            }
        }

        composable("profile") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                ProfileScreen(navController = navController)
            }
        }

        composable("onboarding") { backStackEntry ->
            AnimatedContent(targetState = backStackEntry, transitionSpec = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() with
                        slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            }, label = "") {
                OnboardingScreen(navController = navController)
            }
        }
    }
}