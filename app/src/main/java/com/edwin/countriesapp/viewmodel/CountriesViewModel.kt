package com.edwin.countriesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.countriesapp.repository.restcountries.CountriesRepository
import com.edwin.countriesapp.repository.restcountries.model.RequestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val countriesRepository: CountriesRepository) :
    ViewModel() {

    private val _countries: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val countries: StateFlow<List<String>>
        get() = _countries

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            when (val response = countriesRepository.loadCountries()) {
                is RequestResponse.OnSuccess -> {
                    _countries.value = response.response
                }
                is RequestResponse.OnError -> {

                }
            }
        }
    }

}