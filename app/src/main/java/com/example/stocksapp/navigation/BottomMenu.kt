package com.example.stocksapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenu(val route: String, val icon: ImageVector, val title: String) {
    data object Gainers: BottomMenu(AppScreens.ExploreScreen.name,icon= Icons.Outlined.Home, "TOP GAINERS" )
    data object Losers: BottomMenu(AppScreens.ExploreScreen.name,icon= Icons.Outlined.Home, "TOP LOSERS" )
}