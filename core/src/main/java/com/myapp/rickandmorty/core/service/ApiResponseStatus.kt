package com.myapp.rickandmorty.core.service

sealed class ResponseApi<T> {
    class Loading<T> : ResponseApi<T>()
    class Success<T>(val data: T?) : ResponseApi<T>()
    class Error<T>(val message: String?) : ResponseApi<T>()
}