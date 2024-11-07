package com.example.procareermob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.procareermob.ui.theme.ProCareerMobTheme
import com.example.procareermob.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProCareerMobTheme { // Применяем тему
                AppNavigation()
            }
        }
    }
}