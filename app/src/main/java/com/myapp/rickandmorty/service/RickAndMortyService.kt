package com.myapp.rickandmorty.service

import com.myapp.rickandmorty.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("/api/character/")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("/api/character/")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterResponse>

}