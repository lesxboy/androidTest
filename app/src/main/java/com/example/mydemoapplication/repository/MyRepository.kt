package com.example.mydemoapplication.repository

import com.example.mydemoapplication.data.remote.MyApi
import com.example.mydemoapplication.data.remote.respones.Characters
import com.example.mydemoapplication.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MyRepository @Inject constructor(
    private val api:MyApi
) {

    suspend fun getCharacterList(): Resource<Characters>{
        val response = try {
            api.fetchCharters()
        } catch(e: Exception) {
            return Resource.Error("An error")
        }
        return Resource.Success(response)
    }
}