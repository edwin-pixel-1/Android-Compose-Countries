package com.edwin.countriesapp.repository.restcountries

import com.edwin.countriesapp.network.NetworkManager
import com.edwin.countriesapp.repository.restcountries.datasource.local.LocalStorage
import com.edwin.countriesapp.repository.restcountries.datasource.local.db.CountryDetailsEntity
import com.edwin.countriesapp.repository.restcountries.datasource.remote.RemoteStorage
import com.edwin.countriesapp.repository.restcountries.model.GenericErrorResponse
import com.edwin.countriesapp.repository.restcountries.model.RequestResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val localStorage: LocalStorage,
    private val remoteStorage: RemoteStorage,
    private val networkManager: NetworkManager,
) {

    suspend fun loadCountries(): RequestResponse<List<String>> = withContext(Dispatchers.IO) {
        localStorage.loadCountries()
    }

    suspend fun loadCountryDetails(country: String): RequestResponse<List<CountryDetailsEntity>> =
        withContext(Dispatchers.IO) {
            if (networkManager.isConnected()) {
                remoteStorage.loadCountryDetails(country).apply {
                    if (this is RequestResponse.OnSuccess) {
                        localStorage.saveCountryDetails(country, this.response.first())
                    }
                }
            } else {
                localStorage.loadCountryDetails(country)?.let {
                    RequestResponse.OnSuccess(listOf(it))
                } ?: RequestResponse.OnError(GenericErrorResponse(0, "Network Error"))
            }
        }

}