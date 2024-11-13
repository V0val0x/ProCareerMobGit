package com.example.procareermob

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.procareermob.ui.theme.ProCareerMobTheme
import com.example.procareermob.ui.navigation.AppNavigation
import com.example.procareermob.ui.screens.MainScreen
import com.example.procareermob.ui.theme.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        }

        setContent {
            ProCareerMobTheme { // Применяем тему
                AppNavigation()
            }
        }
    }
}