package com.edwin.countriesapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.edwin.countriesapp.ui.common.UIEvent
import com.edwin.countriesapp.ui.screens.countries.CountriesScreen
import com.edwin.countriesapp.ui.screens.countrydetail.CountryDetailScreen


fun NavGraphBuilder.countriesNavigation(uiEventHandler: (UIEvent) -> Unit, root: String) {
    val countriesScreen = Routes.Countries(root)
    val countryDetailScreen = Routes.CountryDetail(root)

    navigation(
        startDestination = countriesScreen.route,
        route = root
    ) {
        composable(countriesScreen) {
            CountriesScreen(
                uiEventHandler = uiEventHandler,
                detailScreenPath = "$root/${countryDetailScreen.destination}")
        }

        composable(countryDetailScreen) { backStackEntry ->
            backStackEntry.arguments?.getString(NavArg.CountryName.key)?.also {
                CountryDetailScreen(
                    uiEventHandler = uiEventHandler,
                    countryName = it)
            }
        }
    }
}