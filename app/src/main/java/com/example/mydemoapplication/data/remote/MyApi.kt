package com.example.mydemoapplication.data.remote
import com.example.mydemoapplication.data.remote.respones.Characters
import retrofit2.http.GET

interface MyApi {
    @GET("character")
    suspend fun fetchCharters(): Characters
}