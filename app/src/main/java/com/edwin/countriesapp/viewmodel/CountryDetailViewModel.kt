package com.edwin.countriesapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.countriesapp.repository.restcountries.CountriesRepository
import com.edwin.countriesapp.repository.restcountries.model.RequestResponse
import com.edwin.countriesapp.viewmodel.uistate.CountryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(private val countriesRepository: CountriesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CountryDetailState())
    val uiState: StateFlow<CountryDetailState>
        get() = _uiState

    fun loadCountryDetails(country: String) {
        viewModelScope.launch {
            when (val response = countriesRepository.loadCountryDetails(country)) {
                is RequestResponse.OnSuccess -> {
                    response.response.first().apply {
                        _uiState.value =
                            _uiState.value.copy(
                                capital = capital ?: "--",
                                population = "${population ?: "--"}",
                                area = "${area ?: "--"}",
                                region = region ?: "--",
                                subRegion = subRegion ?: "--",
                                flag = flag ?: "",
                                coordinates = coordinates
                            )
                    }
                    Log.d("CountryDetailViewModel", "${response.response.size}")
                }
                is RequestResponse.OnError -> {
                    _uiState.value = _uiState.value.copy(errorAlert = response.error)
                    Log.d("CountryDetailViewModel", "Error - ${response.error.message}")
                }
            }
        }
    }

}