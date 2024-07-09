package com.example.stocksapp.navigation

import android.window.SplashScreen

enum class AppScreens {
    ExploreScreen,
    ProductScreen,
    SearchScreen;
    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            ExploreScreen.name -> ExploreScreen
            ProductScreen.name-> ProductScreen
            SearchScreen.name->SearchScreen
            null ->  ExploreScreen
            else -> throw IllegalArgumentException("Route is ${route} is not recognized")
        }
    }
}