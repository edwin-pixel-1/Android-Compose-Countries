package com.edwin.countriesapp.ui.screens.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.edwin.countriesapp.ui.common.Screen
import com.edwin.countriesapp.ui.navigation.BottomNavItems
import com.edwin.countriesapp.ui.navigation.MainNavigation
import com.edwin.countriesapp.ui.theme.CountriesAppTheme
import com.edwin.countriesapp.viewmodel.SharedViewModel

@Composable
fun DashboardScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    Screen {
        CountriesAppTheme {
            val navController = rememberNavController()
            SetupLayout(
                sharedViewModel = sharedViewModel,
                navigateUp = navController::navigateUp) {
                Screen(modifier = Modifier.padding(it)) {
                    MainNavigation(navController)
                }
            }
        }
    }
}

@Composable
private fun SetupLayout(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {

    val topBarState by sharedViewModel.topBarState.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavItems.values().forEach { navItem ->
                    val title = stringResource(id = navItem.title)
                    BottomNavigationItem(
                        selected = false,
                        onClick = { },
                        icon = { Icon(navItem.icon, title) },
                        label = { Text(title) }
                    )
                }
            }
        },
        topBar = {
            TopAppBar(title = {
                Text(topBarState.title.ifEmpty { "" })
            },
                navigationIcon = if (topBarState.navigationIconEnabled) {
                    {
                        IconButton(onClick = navigateUp) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "")
                        }
                    }
                } else null)
        },
        content = content)
}