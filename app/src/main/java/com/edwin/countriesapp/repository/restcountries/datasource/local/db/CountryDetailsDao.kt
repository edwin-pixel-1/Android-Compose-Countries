package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CountryDetailsEntity)

    @Query("SELECT * FROM countryDetails WHERE name = :countryName")
    suspend fun getDetails(countryName: String): CountryDetailsEntity?

}