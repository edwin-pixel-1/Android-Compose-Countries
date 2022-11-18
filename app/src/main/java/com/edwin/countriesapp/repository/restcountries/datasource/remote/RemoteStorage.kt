package com.edwin.countriesapp.repository.restcountries.datasource.remote

import com.edwin.countriesapp.repository.restcountries.datasource.local.db.CountryDetailsEntity
import com.edwin.countriesapp.repository.restcountries.model.CountryDetailsResponse
import com.edwin.countriesapp.repository.restcountries.model.GenericErrorResponse
import com.edwin.countriesapp.repository.restcountries.model.RequestResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import javax.inject.Inject

class RemoteStorage @Inject constructor(private val countriesAPIService: CountriesAPIService) {

    private val gson: Gson = Gson()

    suspend fun loadCountryDetails(country: String): RequestResponse<List<CountryDetailsEntity>> {
        val response = countriesAPIService.loadCountryDetails(country)

        return validateResponse(response)
    }

    private fun validateResponse(response: Response<List<CountryDetailsResponse>>): RequestResponse<List<CountryDetailsEntity>> =
        if (!response.isSuccessful) { // Failure case
            handleError(response)
        } else {
            RequestResponse.OnSuccess(response.body()?.map {
                CountryDetailsEntity(0,
                    it.name?.common,
                    it.capital?.first(),
                    it.population,
                    it.area,
                    it.region,
                    it.subRegion,
                    it.flags?.png,
                    it.coordinates)
            }!!)
        }

    private fun handleError(response: Response<List<CountryDetailsResponse>>): RequestResponse<List<CountryDetailsEntity>> {
        val code = response.code()
        return response.errorBody()?.let { body ->
            try {
                val parse = gson.fromJson(body.string(), GenericErrorResponse::class.java)
                parse.code = code
                RequestResponse.OnError(parse)
            } catch (e: JsonSyntaxException) {
                RequestResponse.OnError(GenericErrorResponse(code,
                    "JsonSyntaxException - ${e.message}"))
            }
        } ?: RequestResponse.OnError(GenericErrorResponse(code,
            "Error - Error Body Null"))
    }

}