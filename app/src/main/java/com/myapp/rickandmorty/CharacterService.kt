package com.myapp.rickandmorty

import com.myapp.rickandmorty.data.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val api: APIService
) {
    suspend fun getCharacters(): List<CharacterModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getCharacters()
            response.body()?.characters ?: emptyList()
        }
    }

    suspend fun getCharactersByName(name: String): List<CharacterModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getCharactersByName(name)
            response.body()?.characters ?: emptyList()
        }
    }
}