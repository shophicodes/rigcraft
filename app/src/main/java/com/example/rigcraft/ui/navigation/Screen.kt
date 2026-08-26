package com.example.rigcraft.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.rigcraft.R

sealed class Screen(
    val route: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val badgeCount: Int? = null // for items amount in cart
) {
    object Home: Screen("home", R.string.nav_home, R.drawable.home_24px)
    object Wishlist: Screen("wishlist", R.string.nav_wishlist, R.drawable.favorite_24px)
    object Search: Screen("search", R.string.nav_search, R.drawable.search_24px)
    // TO DO: Implement dynamic badge state with a ViewModel
    object Cart: Screen("cart", R.string.nav_cart, R.drawable.shopping_cart_24px, badgeCount = 5)
    object Profile: Screen("profile", R.string.nav_profile, R.drawable.person_24px)

    // For Authentication
    object Login: Screen("login", R.string.nav_login, 0)
    object Register: Screen("register", R.string.nav_register, 0)

    // Product Details
    object ProductDetails : Screen("details/{productId}", 0, 0) {
        fun createRoute(productId: String) = "details/$productId"
    }
    companion object {
        val bottomNavItems = listOf(Home, Wishlist, Search, Cart, Profile)
    }
}
