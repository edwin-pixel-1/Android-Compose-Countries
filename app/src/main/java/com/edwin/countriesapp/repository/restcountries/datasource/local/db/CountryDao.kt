package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CountryEntity)

    @Query("SELECT * FROM country")
    fun getAll(): List<CountryEntity>
}