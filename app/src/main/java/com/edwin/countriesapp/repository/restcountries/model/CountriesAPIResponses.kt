package com.edwin.countriesapp.repository.restcountries.model

import com.google.gson.annotations.SerializedName

data class CountryDetailsResponse(

    @SerializedName("name")
    val name: CountryName? = null,

    @SerializedName("capital")
    val capital: List<String>? = null,

    @SerializedName("population")
    val population: Int? = null,

    @SerializedName("area")
    val area: Float? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("subregion")
    val subRegion: String? = null,

    @SerializedName("flags")
    val flags: CountryFlags? = null,

    @SerializedName("latlng")
    val coordinates: List<Double>? = null,
)

data class CountryName(
    val common: String? = null,
    val official: String? = null,
)

data class CountryFlags(
    val png: String? = null,
)

class GenericErrorResponse(
    var code: Int? = null,

    @SerializedName("message")
    val message: String? = null,
)