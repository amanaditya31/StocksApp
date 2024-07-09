package com.example.stocksapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stocksapp.screens.ExploreScreen.ExploreScreen
import com.example.stocksapp.screens.ExploreScreen.ExploreScreenViewModel
import com.example.stocksapp.screens.ProductScreen.ProductScreen
import com.example.stocksapp.screens.SearchScreen.SearchScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination =AppScreens.ExploreScreen.name ) {
        composable(AppScreens.ExploreScreen.name){
            val ExploreViewModel= hiltViewModel<ExploreScreenViewModel>()
            ExploreScreen(navController=navController, ExploreViewModel)
        }
        composable(AppScreens.ProductScreen.name){
            ProductScreen(navController=navController)
        }
        composable(AppScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
    }
}

