package com.edwin.countriesapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.edwin.countriesapp.R

enum class BottomNavItems(val icon: ImageVector, val route: Routes, @StringRes val title: Int) {
    COUNTRIES(Icons.Default.Home, Routes.Countries(), R.string.countries)
}