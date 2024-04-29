package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.data.repository.UserRepository
import com.myapp.rickandmorty.core.dataStore.DataStoreManager
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class ValidateUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val dataStore: DataStoreManager
) {
    suspend operator fun invoke(email: String, password: String) = validateUser(email, password)

    private suspend fun validateUser(email: String, passwordToCheck: String): Boolean {
        val user = repository.getUserByEmail(email)

        return if (user == null || !BCrypt.checkpw(passwordToCheck, user.password)) {
            false
        } else {
            dataStore.saveSessionID(user.uuid)
            true
        }
    }
}