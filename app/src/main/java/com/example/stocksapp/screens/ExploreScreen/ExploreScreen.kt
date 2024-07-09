package com.example.stocksapp.screens.ExploreScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stocksapp.components.BottomMenu
import com.example.stocksapp.navigation.BottomMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController,
                  viewModel: ExploreScreenViewModel= hiltViewModel()){
    var counter by remember{ mutableStateOf(0) }

    val items = listOf(
        BottomMenu.Gainers,
        BottomMenu.Losers
    )

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
            BottomNavigation() {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen->
                    BottomNavigationItem(
                        modifier = Modifier.padding(bottom = 20.dp),
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { androidx.compose.material.Text(text=screen.title) },
                        selectedContentColor= Color.Black,
                        unselectedContentColor= Color.Gray,
                        selected = currentRoute==screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                navController.graph.startDestinationRoute?.let{
                                        route->
                                    popUpTo(route){
                                        saveState=true
                                    }
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                                counter=screen.index
                            }

                        }
                    )
                }
            }
        },

    ) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding)) {
            ScreenContent( viewModel, counter)
        }


    }
}

@Composable
fun ScreenContent(viewModel: ExploreScreenViewModel= hiltViewModel(), counter:Int) {
    var list1=viewModel.list1
    var list2=viewModel.list2

    LazyVerticalGrid(modifier = Modifier.fillMaxSize().padding(top=30.dp), columns = GridCells.Fixed(2) ){
    if(counter==0) {
        items(list1) { listItem ->
            ElevatedCard(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
                modifier = Modifier
                    .padding(16.dp)
                    .height(242.dp)
                    .width(202.dp)
                    .clickable { }
            ) {
                Text(
                    text = listItem.ticker,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

        }
    }else{
        items(list2) { listItem ->
            ElevatedCard(shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
                modifier = Modifier
                    .padding(16.dp)
                    .height(242.dp)
                    .width(202.dp)
                    .clickable { }
            ) {
                Text(
                    text = listItem.ticker,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
    }
    list1.forEach(){
        Log.d("list1", "{${it.ticker}}")

    }
}
