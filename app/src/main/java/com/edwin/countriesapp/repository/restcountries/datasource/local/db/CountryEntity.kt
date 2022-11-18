package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)
