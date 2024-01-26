package com.example.mydemoapplication.di

import com.example.mydemoapplication.data.remote.MyApi
import com.example.mydemoapplication.repository.MyRepository
import com.example.mydemoapplication.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi(): MyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMyRepository(api: MyApi) = MyRepository(api)
}
