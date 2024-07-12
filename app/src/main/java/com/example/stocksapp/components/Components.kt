package com.example.stocksapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stocksapp.navigation.BottomMenu


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(modifier :Modifier= Modifier, loading: Boolean =false,
               hint: String= "Search",
               onSearch:(String)-> Unit={}){
    Column {
        val searchQueryState= rememberSaveable{ mutableStateOf("") }
        val keyboardController= LocalSoftwareKeyboardController.current
        val valid= remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }
        InputField(valueState = searchQueryState, labelId ="Search" , enabled =true ,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value=""
                keyboardController?.hide()
            }
        )

    }
}

@Composable
fun InputField(
    modifier: Modifier=Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean=true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default

) {
    OutlinedTextField(
        value=(valueState.value),
        onValueChange ={ valueState.value = it},
        label ={ androidx.compose.material3.Text(text=labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color= MaterialTheme.colorScheme.onBackground),
        modifier= Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
    )
}


@Composable
fun BottomMenu(navController: NavController, counter: Int) {
    val items = listOf(
        BottomMenu.Gainers,
        BottomMenu.Losers
    )

    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen->
            BottomNavigationItem(
                modifier = Modifier.padding(bottom = 20.dp),
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(text=screen.title) },
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
                    }

                }
            )
        }
    }
}
