package com.example.stocksapp.screens.ExploreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stocksapp.navigation.AppScreens
import com.example.stocksapp.navigation.BottomMenu
import com.example.stocksapp.ui.theme.LightGreen
import com.example.stocksapp.ui.theme.LightRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController,
                  viewModel: ExploreScreenViewModel= hiltViewModel()){
    var counter by remember{ mutableStateOf(0) }

    val items = listOf(
        BottomMenu.Gainers ,
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
                    Row() {
                        Text("STOCKS APP",Modifier.padding(20.dp))
                        Spacer(modifier=Modifier.width(120.dp))
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search",
                            tint = Color.Gray.copy(alpha=0.7f),
                            modifier = Modifier
                                .padding(20.dp)
                                .clickable { navController.navigate(AppScreens.SearchScreen.name) })
                    }
                }



            )
        },
        bottomBar = {
            BottomNavigation() {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen->
                    BottomNavigationItem(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(bottom = 30.dp),
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { androidx.compose.material.Text(text=screen.title) },
                        selectedContentColor= MaterialTheme.colorScheme.secondary,
                        unselectedContentColor= MaterialTheme.colorScheme.onBackground,
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
        Column(
            Modifier.background(color=MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(innerPadding)) {
            ScreenContent( viewModel,navController,counter)
        }


    }
}

@Composable
fun ScreenContent(
    viewModel: ExploreScreenViewModel = hiltViewModel(),
    navController: NavController,
    counter: Int
) {
    var list1 = viewModel.list1
    var list2 = viewModel.list2
    if (viewModel.isloading) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Loading..")
            LinearProgressIndicator()
        }
    } else {
        Text(
            text = if (counter == 0) {
                "Top Gainers"
            }else{
                "Top Losers"
            },     modifier = Modifier.fillMaxWidth()
                .padding(top=16.dp, bottom=16.dp,end=16.dp,start=25.dp),
            fontWeight = FontWeight.Bold,
            fontSize=20.sp
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            columns = GridCells.Fixed(2)
        ) {
            if (counter == 0) {
                items(list1) { listItem ->
                    ElevatedCard(shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .height(202.dp)
                            .width(202.dp)
                            .clickable {
                                navController.navigate(AppScreens.ProductScreen.name + "/${listItem.ticker}" + "/${listItem.price}")
                            }
                    ) {
                            Text(
                                text = listItem.ticker,
                                modifier = Modifier
                                    .padding(start=20.dp, top=70.dp, bottom=16.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize=25.sp

                            )
                            Text(
                                text = "Rs. "+ listItem.price,
                                modifier = Modifier
                                    .padding(start=20.dp),
                                textAlign = TextAlign.Center,
                                fontSize=13.sp


                            )
                        Text(
                            text = "+${listItem.change_amount
                                .replace(Regex("""(\.\d{2})\d*"""), "$1")}" +
                                    "(${listItem.change_percentage
                                        .replace(Regex("""(\.\d{2})\d*"""), "$1")}%)",
                            modifier = Modifier
                                .padding(start=20.dp),
                            textAlign = TextAlign.Center,
                            fontSize=10.sp,
                            color = LightGreen

                        )

                    }

                }
            } else {
                items(list2) { listItem ->
                    ElevatedCard(shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .height(202.dp)
                            .width(202.dp)
                            .clickable {
                                navController.navigate(AppScreens.ProductScreen.name + "/${listItem.ticker}" + "/${listItem.price}")
                            }
                    ) {
                        Text(
                            text = listItem.ticker,
                            modifier = Modifier
                                .padding(start=20.dp, top=70.dp, bottom=16.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize=25.sp

                        )
                        Text(
                            text = "Rs. "+ listItem.price,
                            modifier = Modifier
                                .padding(start=20.dp),
                            textAlign = TextAlign.Center,
                            fontSize=13.sp


                        )
                        Text(
                            text = "-${listItem.change_amount
                                .replace(Regex("""(\.\d{2})\d*"""), "$1")}" +
                                    "(${listItem.change_percentage
                                        .replace(Regex("""(\.\d{2})\d*"""), "$1")}%)", //regEx to trim to 2 decimal values
                            modifier = Modifier
                                .padding(start=20.dp),
                            textAlign = TextAlign.Center,
                            fontSize=10.sp,
                            color = LightRed

                        )
                    }

                }
            }
        }
    }
}
