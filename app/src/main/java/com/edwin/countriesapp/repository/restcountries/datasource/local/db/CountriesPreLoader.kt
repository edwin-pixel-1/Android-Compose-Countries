package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.edwin.countriesapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import javax.inject.Provider

class CountriesPreLoader(
    private val context: Context,
    private val countryDaoProvider: Provider<CountryDao>,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            preloadCountriesLocalFile()
        }
    }

    private fun preloadCountriesLocalFile() {
        val countryDao = countryDaoProvider.get()

        try {
            val countriesFile = readCountriesLocalFile()
            val countriesArray = countriesFile.getJSONArray(COUNTRIES_JSON_ARRAY)
            Log.i("Countries preloading", "$countriesArray")
            for (index in 0 until countriesArray.length()) {
                val countryEntity = CountryEntity(name = countriesArray.getString(index))
                countryDao.insert(countryEntity)
            }
        } catch (exception: JSONException) {
            Log.i("Countries preloading", "Failed to preload the countries data $exception")
        }
    }

    private fun readCountriesLocalFile(): JSONObject {
        val inputStream = context.resources.openRawResource(R.raw.countries)

        BufferedReader(inputStream.reader()).use {
            return JSONObject(it.readText())
        }
    }
}

private const val COUNTRIES_JSON_ARRAY = "string-array"