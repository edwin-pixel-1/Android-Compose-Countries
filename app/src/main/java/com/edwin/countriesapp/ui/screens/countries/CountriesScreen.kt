package com.edwin.countriesapp.ui.screens.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.edwin.countriesapp.R
import com.edwin.countriesapp.ui.common.Screen
import com.edwin.countriesapp.ui.common.UIEvent
import com.edwin.countriesapp.viewmodel.CountriesViewModel

@Composable
fun CountriesScreen(
    uiEventHandler: (UIEvent) -> Unit,
    detailScreenPath: String,
    countriesViewModel: CountriesViewModel = hiltViewModel(),
) {
    uiEventHandler(UIEvent.UpdateTopBar(title = stringResource(id = R.string.countries_screen_title),
        navigationIconEnabled = false))

    val countries by countriesViewModel.countries.collectAsState()

    Screen(backgroundColor = colorResource(id = R.color.screen_background)) {
        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.country_item_spacing))) {
            items(countries) { country ->
                CountryListItem(country = country) {
                    uiEventHandler(UIEvent.NavigateTo("$detailScreenPath/$country"))
                }
            }
        }
    }
}

@Composable
fun CountryListItem(country: String, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(enabled = true, onClick = onClick)
        .padding(horizontal = dimensionResource(id = R.dimen.country_item_margin_horizontal))
        .border(dimensionResource(id = R.dimen.country_item_border),
            colorResource(id = R.color.country_item_border))
        .background(colorResource(id = R.color.country_item_background))
        .padding(vertical = dimensionResource(id = R.dimen.country_item_padding_vertical)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(modifier = Modifier, text = country, color = Color.White)
    }
}