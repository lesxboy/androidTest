package com.example.mydemoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mydemoapplication.mylist.ListScreen
import com.example.mydemoapplication.mylist.ListViewModel
import com.example.mydemoapplication.navigation.AppNavHost
import com.example.mydemoapplication.navigation.NavigationItem
import com.example.mydemoapplication.ui.theme.MyDemoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyDemoApplicationTheme {
                val navController = rememberNavController()

                //ListScreen(navController)


                NavHost(
                    navController = navController,
                    startDestination = "list",
                ) {

                    composable("list") {
                        ListScreen(navController)
                    }
                    composable("list2") {
                        ListScreen(navController)
                    }
                }
            /*
               NavHost(navController = rememberNavController(), startDestination = "list") {
                   composable("list") {
                       ListScreen(
                           navController,
                           viewModel<ListViewModel>()
                       )
                   }
                   composable("detail") {
                       ListScreen(
                           rememberNavController(),
                           viewModel<ListViewModel>()
                       )
                   }
               }*/
            }
        }
    }
}