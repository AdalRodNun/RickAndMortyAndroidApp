package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.UserRepository
import javax.inject.Inject

class GetUserByEmail @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String) = repository.getUserByEmail(email)
}