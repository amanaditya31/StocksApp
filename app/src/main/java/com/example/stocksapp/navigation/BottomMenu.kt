package com.example.stocksapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LineAxis
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenu(val route: String, val icon: ImageVector, val title: String, val index:Int) {
    data object Gainers: BottomMenu(AppScreens.ExploreScreen.name,icon= Icons.Outlined.StackedLineChart, "TOP GAINERS" , index = 0)
    data object Losers: BottomMenu(AppScreens.ExploreScreen.name,icon= Icons.Outlined.LineAxis, "TOP LOSERS" , index=1)
}