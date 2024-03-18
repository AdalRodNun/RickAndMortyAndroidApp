package com.myapp.rickandmorty.service

import com.myapp.rickandmorty.data.model.CharacterModel
import com.myapp.rickandmorty.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("/api/character/")
    suspend fun getAllCharacters(
        @Query("name") name: String,
        @Query("page") page: Int
    ): Response<CharacterResponse>

    @GET("/api/character/{id}")
    suspend fun getCharacterByID(
        @Path("id") id: Int
    ): Response<CharacterModel>
}