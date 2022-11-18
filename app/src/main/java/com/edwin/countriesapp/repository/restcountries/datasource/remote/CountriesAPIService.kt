package com.edwin.countriesapp.repository.restcountries.datasource.remote

import com.edwin.countriesapp.repository.restcountries.model.CountryDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesAPIService {

    @GET("name/{country}")
    suspend fun loadCountryDetails(@Path("country") country: String): Response<List<CountryDetailsResponse>>

}