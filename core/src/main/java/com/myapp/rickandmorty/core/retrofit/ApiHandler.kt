package com.myapp.rickandmorty.core.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiHandler @Inject constructor(){
    suspend fun <T : Any> handleApi(execute: suspend () -> Response<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            ApiResponse.Loading<T>()
            try {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    ApiResponse.Success(body)
                } else {
                    ApiResponse.Error(response.message())
                }
            } catch (e: HttpException) {
                ApiResponse.Error(e.message)
            } catch (e: Throwable) {
                ApiResponse.Error(e.message)
            }
        }
    }
}