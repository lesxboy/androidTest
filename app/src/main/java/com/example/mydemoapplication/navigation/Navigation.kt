package com.example.mydemoapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mydemoapplication.mylist.ListScreen

enum class Screen {
    LIST,
    DETAIL,
}
sealed class NavigationItem(val route: String) {
    object List : NavigationItem(Screen.LIST.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
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
            ListScreen(navController)
        }
    }
}

object NavigationObj {
    private lateinit var navController: NavHostController

    fun setController(controller: NavHostController) {
        navController = controller
    }

    fun getController(): NavHostController {
        return navController
    }
}