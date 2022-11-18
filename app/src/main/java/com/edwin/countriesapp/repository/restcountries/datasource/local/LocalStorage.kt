package com.edwin.countriesapp.repository.restcountries.datasource.local

import com.edwin.countriesapp.repository.restcountries.datasource.local.db.CountryDetailsEntity
import com.edwin.countriesapp.repository.restcountries.model.RequestResponse

abstract class LocalStorage {

    abstract fun loadCountries(): RequestResponse<List<String>>

    abstract suspend fun loadCountryDetails(country: String): CountryDetailsEntity?

    abstract suspend fun saveCountryDetails(
        countryName: String,
        countryDetails: CountryDetailsEntity,
    )
}