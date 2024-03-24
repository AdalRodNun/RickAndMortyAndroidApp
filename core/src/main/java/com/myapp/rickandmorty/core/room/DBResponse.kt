package com.myapp.rickandmorty.core.room

sealed class DBResponse<T> {
    class Success<T>(val data: T? = null) : DBResponse<T>()
    class Error<T>(val message: String) : DBResponse<T>()
}