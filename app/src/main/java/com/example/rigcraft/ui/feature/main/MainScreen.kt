package com.example.rigcraft.ui.feature.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.example.rigcraft.R
import com.example.rigcraft.ui.feature.auth.AuthViewModel
import com.example.rigcraft.ui.navigation.NavGraph
import com.example.rigcraft.ui.navigation.Screen
import com.example.rigcraft.ui.theme.RigCraftTheme

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isUserLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    MainScreenContent(
        navController = navController,
        isUserLoggedIn = isUserLoggedIn
    )
}

@Composable
fun MainScreenContent(
    navController: NavHostController,
    isUserLoggedIn: Boolean?
) {
    if (isUserLoggedIn == null) {
        // Loading state
        return
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Hide bottom bar on detail/checkout screens if needed
    val showBottomBar = Screen.bottomNavItems.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar && isUserLoggedIn) {
                NavigationBar {
                    Screen.bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (screen.badgeCount != null && screen.badgeCount > 0) {
                                            Badge {
                                                val countText =
                                                    if (screen.badgeCount > 99) stringResource(R.string.badge_max_count) else screen.badgeCount.toString()
                                                Text(text = countText)
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(id = screen.iconRes),
                                        contentDescription = stringResource(screen.titleRes)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = if (isUserLoggedIn) Screen.Home.route else Screen.Login.route
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    RigCraftTheme {
        MainScreenContent(
            navController = rememberNavController(),
            isUserLoggedIn = true
        )
    }
}