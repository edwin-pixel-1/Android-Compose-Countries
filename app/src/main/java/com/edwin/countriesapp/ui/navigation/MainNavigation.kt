package com.edwin.countriesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.edwin.countriesapp.ui.common.UIEvent
import com.edwin.countriesapp.viewmodel.SharedViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    NavHost(navController = navController, startDestination = Routes.Dashboard.route) {
        BottomNavItems.values().forEach {
            this.bottomNavigation(it) { event ->
                when (event) {
                    is UIEvent.NavigateTo -> {
                        navController.navigate(event.destination)
                    }
                    is UIEvent.UpdateTopBar -> {
                        sharedViewModel.updateTopBar(event.title, event.navigationIconEnabled)
                    }
                    UIEvent.NavigateUp -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}

private fun NavGraphBuilder.bottomNavigation(
    navDestination: BottomNavItems,
    uiEventHandler: (UIEvent) -> Unit,
) {
    when (navDestination) {
        BottomNavItems.COUNTRIES -> {
            countriesNavigation(uiEventHandler, Routes.Dashboard.route)
        }
    }
}


fun NavGraphBuilder.composable(
    route: Routes,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = route.route,
        arguments = route.args,
        content = content
    )
}