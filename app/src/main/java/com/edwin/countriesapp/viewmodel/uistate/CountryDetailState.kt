package com.edwin.countriesapp.viewmodel.uistate

import com.edwin.countriesapp.repository.restcountries.model.GenericErrorResponse

data class CountryDetailState(

    val name: String = "",
    val capital: String = "",
    val population: String = "",
    val area: String = "",
    val region: String = "",
    val subRegion: String = "",
    val flag: String = "",
    val coordinates: List<Double>? = null,
    val errorAlert: GenericErrorResponse? = null,

    )