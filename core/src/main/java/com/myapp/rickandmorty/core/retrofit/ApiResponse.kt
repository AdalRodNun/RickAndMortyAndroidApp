package com.myapp.rickandmorty.core.retrofit

sealed class ApiResponse<T> {
    class Loading<T> : ApiResponse<T>()
    class Success<T>(val data: T?) : ApiResponse<T>()
    class Error<T>(val message: String?) : ApiResponse<T>()
}