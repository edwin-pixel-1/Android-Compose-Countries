package com.edwin.countriesapp.repository.restcountries.model

sealed class RequestResponse<T> {

    data class OnSuccess<T>(val response: T) : RequestResponse<T>()

    data class OnError<T>(val error: GenericErrorResponse) : RequestResponse<T>()

}