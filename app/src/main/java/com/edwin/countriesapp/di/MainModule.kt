package com.edwin.countriesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.edwin.countriesapp.BuildConfig
import com.edwin.countriesapp.network.NetworkManager
import com.edwin.countriesapp.repository.restcountries.CountriesRepository
import com.edwin.countriesapp.repository.restcountries.datasource.local.LocalStorage
import com.edwin.countriesapp.repository.restcountries.datasource.local.LocalStorageImp
import com.edwin.countriesapp.repository.restcountries.datasource.local.db.AppDataBase
import com.edwin.countriesapp.repository.restcountries.datasource.local.db.CountriesPreLoader
import com.edwin.countriesapp.repository.restcountries.datasource.local.db.CountryDao
import com.edwin.countriesapp.repository.restcountries.datasource.remote.CountriesAPIService
import com.edwin.countriesapp.repository.restcountries.datasource.remote.RemoteStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(null)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideCountriesAPIService(retrofit: Retrofit): CountriesAPIService =
        retrofit.create(CountriesAPIService::class.java)

    @Provides
    fun provideLocalStorage(appDataBase: AppDataBase): LocalStorage = LocalStorageImp(appDataBase)

    @Provides
    fun provideRemoteStorage(countriesAPIService: CountriesAPIService): RemoteStorage =
        RemoteStorage(countriesAPIService)

    @Provides
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager =
        NetworkManager(context)

    @Provides
    fun provideCountriesRepository(
        localStorage: LocalStorage,
        remoteStorage: RemoteStorage,
        networkManager: NetworkManager,
    ): CountriesRepository = CountriesRepository(localStorage = localStorage,
        remoteStorage = remoteStorage,
        networkManager = networkManager)

    @Provides
    @Singleton
    fun provideDatabase(application: Application, provider: Provider<CountryDao>): AppDataBase {
        return Room
            .databaseBuilder(application, AppDataBase::class.java, "country.db")
            .addCallback(CountriesPreLoader(application, provider))
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDataBase): CountryDao = database.countryDao
}