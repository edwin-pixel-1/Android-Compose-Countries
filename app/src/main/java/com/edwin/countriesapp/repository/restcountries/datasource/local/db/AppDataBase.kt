package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CountryEntity::class, CountryDetailsEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val countryDao: CountryDao

    abstract val countryDetailsDao: CountryDetailsDao

}