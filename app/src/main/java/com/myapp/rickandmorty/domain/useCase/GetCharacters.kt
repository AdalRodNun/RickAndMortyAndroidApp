package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.CharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke() = repository.getAllCharacters(1)

}