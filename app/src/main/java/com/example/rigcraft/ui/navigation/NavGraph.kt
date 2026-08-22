package com.example.rigcraft.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rigcraft.ui.feature.auth.AuthViewModel
import com.example.rigcraft.ui.feature.auth.LoginScreen
import com.example.rigcraft.ui.feature.auth.RegisterScreen
import com.example.rigcraft.ui.feature.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    startDestination: String = Screen.Login.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        modifier = modifier
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Wishlist.route) {}
        composable(route = Screen.Search.route) {}
        composable(route = Screen.Cart.route) {}
        composable(route = Screen.Profile.route) {}
    }
}
