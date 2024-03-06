package com.myapp.rickandmorty.core.useCase

import com.myapp.rickandmorty.core.repository.UserRepository

class GetUserByEmail(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String) = userRepository.getUserByEmail(email)
}