package com.edwin.countriesapp.ui.screens.countrydetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.edwin.countriesapp.R
import com.edwin.countriesapp.ui.common.AlertDialogCustom
import com.edwin.countriesapp.ui.common.Screen
import com.edwin.countriesapp.ui.common.UIEvent
import com.edwin.countriesapp.viewmodel.CountryDetailViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CountryDetailScreen(
    uiEventHandler: (UIEvent) -> Unit,
    countryName: String,
    countryDetailsViewModel: CountryDetailViewModel = hiltViewModel(),
) {
    uiEventHandler(UIEvent.UpdateTopBar(
        title = stringResource(id = R.string.country_detail_screen_title, countryName),
        navigationIconEnabled = true))

    LaunchedEffect(Unit) {
        countryDetailsViewModel.loadCountryDetails(countryName)
    }

    Screen {
        val uiState by countryDetailsViewModel.uiState.collectAsState()
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            DetailsHeader(countryName = uiState.name, flagUrl = uiState.flag)
            Spacer(modifier = Modifier.height(30.dp))
            DetailsRow(
                label = stringResource(id = R.string.country_detail_screen_label_1),
                value = uiState.capital
            )
            DetailsRow(
                label = stringResource(id = R.string.country_detail_screen_label_2),
                value = uiState.population
            )
            DetailsRow(
                label = stringResource(id = R.string.country_detail_screen_label_3),
                value = uiState.area
            )
            DetailsRow(
                label = stringResource(id = R.string.country_detail_screen_label_4),
                value = uiState.region
            )
            DetailsRow(
                label = stringResource(id = R.string.country_detail_screen_label_5),
                value = uiState.subRegion
            )
            DetailsMap(uiState.name, uiState.coordinates)
        }

        uiState.errorAlert?.apply {
            AlertDialogCustom(title = "Error - ${code.toString()}",
                text = message ?: "Unknown Error",
                onPositive = {
                    uiEventHandler(UIEvent.NavigateUp)
                })
        }
    }
}

@Composable
fun DetailsMap(countryName: String, coordinates: List<Double>?) {
    if (coordinates == null || coordinates.isEmpty() || coordinates.size != 2)
        return

    val location = LatLng(coordinates[0], coordinates[1])
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 4.3f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = location),
            title = countryName,
            snippet = stringResource(id = R.string.country_detail_screen_map_snippet, countryName)
        )
    }
}

@Composable
fun DetailsHeader(countryName: String, flagUrl: String) {
    if (flagUrl.isNotEmpty()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center) {
            AsyncImage(
                model = flagUrl,
                contentDescription = null,
                alignment = Alignment.Center
            )
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.wrapContentWidth(),
            textAlign = TextAlign.Center,
            text = countryName,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DetailsRow(label: String, value: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(enabled = true, state = ScrollState(0))
        .padding(bottom = 20.dp)) {
        Text(
            textAlign = TextAlign.Start,
            text = label,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            textAlign = TextAlign.End,
            text = value,
        )
    }
}