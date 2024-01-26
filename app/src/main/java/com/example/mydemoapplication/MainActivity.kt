package com.example.mydemoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mydemoapplication.screens.mylist.ListScreen
import com.example.mydemoapplication.navigation.AppNavHost
import com.example.mydemoapplication.ui.theme.MyDemoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDemoApplicationTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}