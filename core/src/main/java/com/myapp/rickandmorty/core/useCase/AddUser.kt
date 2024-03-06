package com.myapp.rickandmorty.core.useCase

import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

class AddUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = hashPassword(user)

    private suspend fun hashPassword(user: User) {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        user.password  = hashedPassword
        userRepository.addUser(user)
    }
}