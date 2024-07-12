package com.example.stocksapp.screens.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stocksapp.components.SearchForm
import com.example.stocksapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController,
                 viewModel: SearchScreenViewModel = hiltViewModel()){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(Modifier.padding(top=10.dp, bottom=10.dp)) {
                        SearchForm(modifier= Modifier
                            .fillMaxWidth()
                            .padding(16.dp)){searchQuery->

                            viewModel.loadSearches(searchQuery)
                        }

                    }
                }

            )
        },
    ) { innerPadding ->
        Column(
            Modifier.background(color=MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())) {
            var bestMatchesList = viewModel._stockList.collectAsState()
            if (viewModel.isloading) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "          ")
                    LinearProgressIndicator()
                }
            } else {
                bestMatchesList.value.forEach() {
                    Row(Modifier.padding(5.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD8D8D8),
                            shape = RoundedCornerShape(size = 8.dp)
                        ).clickable {
                            navController.navigate(AppScreens.ProductScreen.name + "/${it.symbol}" + "/${it.marketClose}")
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.StackedLineChart,
                            contentDescription = "Search",
                            tint = Color.Gray.copy(alpha = 0.7f),
                            modifier = Modifier.padding(20.dp)
                        )
                        Column() {
                            Text(
                                it.name,
                                Modifier.padding(start = 5.dp, end = 5.dp),
                                fontSize = 15.sp
                            )
                            Text(it.symbol, Modifier.padding(5.dp), fontSize = 13.sp)
                        }

                    }
                }

            }

        }
    }

}

