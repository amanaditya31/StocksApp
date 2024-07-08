package com.example.stocksapp.screens.ExploreScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    ) { innerPadding ->
        ScreenContent(innerPadding, viewModel)
    }
}

@Composable
fun ScreenContent(innerPadding: PaddingValues,
                  viewModel: ExploreScreenViewModel= hiltViewModel()) {
    var list1=viewModel.list1
    var list2=viewModel.list2
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(list1) {list->
            Text(text =list.ticker)
        }
    }
    list1.forEach(){
        Log.d("list1", "{${it.ticker}}")

    }
}
