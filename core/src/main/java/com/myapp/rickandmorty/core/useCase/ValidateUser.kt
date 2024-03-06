package com.myapp.rickandmorty.core.useCase

import com.myapp.rickandmorty.core.App
import com.myapp.rickandmorty.core.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

class ValidateUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String) = validateUser(email, password)

    private suspend fun validateUser(email: String, passwordToCheck: String): Boolean {
        val user = userRepository.getUserByEmail(email)

        return if (user == null || !BCrypt.checkpw(passwordToCheck, user.password)) {
            false
        } else {
            App.setUserUUID(user.uuid)
            true
        }
    }
}