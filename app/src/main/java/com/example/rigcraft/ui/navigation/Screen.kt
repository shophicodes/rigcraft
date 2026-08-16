package com.example.rigcraft.ui.navigation

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.rigcraft.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int,
    val badgeCount: Int? = null // for items amount in cart
) {
    object Home: Screen("home", "Home", R.drawable.home_24px)
    object Wishlist: Screen("wishlist", "Wishlist", R.drawable.favorite_24px)
    object Search: Screen("search", "Search", R.drawable.search_24px)
    // TO DO: Implement dynamic badge state with a ViewModel
    object Cart: Screen("cart", "Cart", R.drawable.shopping_cart_24px, badgeCount = 5)
    object Profile: Screen("profile", "Profile", R.drawable.person_24px)

    companion object {
        val bottomNavItems = listOf(Home, Wishlist, Search, Cart, Profile)
    }
}