package com.example.onboardingapp.services

import com.example.onboardingapp.services.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/api/character/")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("/api/character/")
    suspend fun getCharactersByName(@Query("name") name: String): Response<CharacterResponse>
}