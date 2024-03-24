package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.R
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.room.DBResponse
import com.myapp.rickandmorty.data.repository.UserRepository
import com.myapp.rickandmorty.core.di.ResourceProvider
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository,
    private val resource: ResourceProvider
) {
    suspend operator fun invoke(user: User) = hashPassword(user)

    private suspend fun hashPassword(user: User): DBResponse<Nothing> {
        val alreadyExist = repository.checkIfUserExist(user.email)

        return if (alreadyExist) {
            DBResponse.Error(resource.getString(R.string.user_already_exist))
        } else {
            val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
            user.password = hashedPassword
            repository.addUser(user)
            DBResponse.Success()
        }
    }
}