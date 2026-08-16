package com.example.rigcraft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
    ) {
        // TO DO: Implement screen function calls inside each composable()
        composable(route = Screen.Home.route) {}
        composable(route = Screen.Wishlist.route) {}
        composable(route = Screen.Search.route) {}
        composable(route = Screen.Cart.route) {}
        composable(route = Screen.Profile.route) {}
    }
}