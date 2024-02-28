package com.myapp.rickandmorty.services

import com.myapp.rickandmorty.core.App.Companion.retrofit
import com.myapp.rickandmorty.services.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/character/")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("/api/character/")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterResponse>

    companion object {
        val service: APIService = retrofit.create(APIService::class.java)
    }
}