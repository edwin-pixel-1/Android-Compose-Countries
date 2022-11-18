package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "countryDetails", indices = [Index(value = ["name"], unique = true)])
data class CountryDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String? = null,

    val capital: String? = null,

    val population: Int? = null,

    val area: Float? = null,

    val region: String? = null,

    val subRegion: String? = null,

    val flag: String? = null,

    val coordinates: List<Double>? = null,
)
