package com.example.mydemoapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mydemoapplication.screens.detail.DetailScreen
import com.example.mydemoapplication.screens.mylist.ListScreen

enum class Screens {
    LIST,
    DETAIL,
}
sealed class NavigationItem(val route: String) {
    object List : NavigationItem(Screens.LIST.name)
    object Detail : NavigationItem(Screens.DETAIL.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.List.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.List.route) {
            ListScreen(navController)
        }
        composable(NavigationItem.Detail.route) {
            DetailScreen(navController)
        }
    }
}