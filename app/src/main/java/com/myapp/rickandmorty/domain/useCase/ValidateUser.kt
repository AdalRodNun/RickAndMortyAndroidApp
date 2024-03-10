package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.UserRepository
import com.myapp.rickandmorty.core.data.UserUUID
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class ValidateUser @Inject constructor(
    private val repository: UserRepository,
    private val userUUID: UserUUID
) {
    suspend operator fun invoke(email: String, password: String) = validateUser(email, password)

    private suspend fun validateUser(email: String, passwordToCheck: String): Boolean {
        val user = repository.getUserByEmail(email)

        return if (user == null || !BCrypt.checkpw(passwordToCheck, user.password)) {
            false
        } else {
            userUUID.setUserUUID(user.uuid)
            true
        }
    }
}