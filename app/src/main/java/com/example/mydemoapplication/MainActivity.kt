package com.example.mydemoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mydemoapplication.mylist.ListScreen
import com.example.mydemoapplication.ui.theme.MyDemoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDemoApplicationTheme {
                ListScreen()
            }
        }
    }
}