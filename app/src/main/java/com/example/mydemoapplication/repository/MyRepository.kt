package com.example.mydemoapplication.repository

import com.example.mydemoapplication.data.remote.MyApi
import com.example.mydemoapplication.data.remote.respones.Characters
import com.example.mydemoapplication.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class MyRepository @Inject constructor(
    private val api:MyApi
) {
    suspend fun getCharacterList(): Resource<Characters>{
        val response = try {
            api.fetchCharters()
        } catch(e: Exception) {
            return when (e) {
                is IOException -> {
                    Resource.Error("IOException error ${e.localizedMessage} ")
                }
                is HttpException -> {
                    Resource.Error("HttpException error ${e.localizedMessage} ")
                } else -> { // Note the block
                    Resource.Error("An error ${e.localizedMessage} ")
                }
            }
        }
        return Resource.Success(response)
    }
}