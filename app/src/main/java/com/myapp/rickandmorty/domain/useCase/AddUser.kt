package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.data.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class AddUser @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) = hashPassword(user)

    private suspend fun hashPassword(user: User) {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        user.password  = hashedPassword
        repository.addUser(user)
    }
}