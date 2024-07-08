package com.example.stocksapp.screens.ExploreScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stocksapp.components.BottomMenu
import com.example.stocksapp.navigation.BottomMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController,
                  viewModel: ExploreScreenViewModel= hiltViewModel()){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("STOCKS APP")
                }

            )
        },
        bottomBar = {
            BottomMenu(navController)
        }
    ) { innerPadding ->
        ScreenContent(innerPadding, viewModel)
    }
}

@Composable
fun ScreenContent(innerPadding: PaddingValues,
                  viewModel: ExploreScreenViewModel= hiltViewModel()) {
    var list1=viewModel.list1
    var list2=viewModel.list2
    list1.forEach(){
        Text(text = it.ticker)
    }
}
