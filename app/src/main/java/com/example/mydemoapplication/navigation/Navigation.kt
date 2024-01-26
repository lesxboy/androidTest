package com.example.mydemoapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mydemoapplication.data.remote.respones.CharResult
import com.example.mydemoapplication.screens.detail.DetailScreen
import com.example.mydemoapplication.screens.mylist.ListScreen
import com.google.gson.Gson

enum class Screens {
    LIST,
    DETAIL,
}
sealed class NavigationItem(val route: String) {
    object List : NavigationItem(Screens.LIST.name)
    object Detail : NavigationItem(Screens.DETAIL.name + "/{id}")
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
        composable(
            NavigationItem.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            it.arguments?.getString("id")?.let { id ->
                DetailScreen(navController = navController, id = id)
            }
        }
    }
}