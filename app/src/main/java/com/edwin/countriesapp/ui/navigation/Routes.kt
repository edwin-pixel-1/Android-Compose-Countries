package com.edwin.countriesapp.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(
    val destination: String,
    val root: String = "",
    navArgs: List<NavArg>? = null,
) {

    val route: String = if (navArgs.isNullOrEmpty()) {
        destination
    } else {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(root)
            .plus(destination)
            .plus(argValues)
            .joinToString("/")
    }

    val args: List<NamedNavArgument> = navArgs?.map {
        navArgument(it.key) {
            this.type = it.navType
        }
    } ?: listOf()

    object Dashboard : Routes(destination = "dashboard")
    data class Countries(val rootPath: String = "") : Routes("countries", rootPath)
    data class CountryDetail(val rootPath: String = "") :
        Routes("countryDetail", rootPath, listOf(NavArg.CountryName))
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    CountryName("countryName", NavType.StringType)
}