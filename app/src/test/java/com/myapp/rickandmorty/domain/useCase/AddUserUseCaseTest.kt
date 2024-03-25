package com.myapp.rickandmorty.domain.useCase

import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.data.repository.UserRepository
import com.myapp.rickandmorty.core.di.ResourceProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddUserUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: UserRepository

    @RelaxedMockK
    private lateinit var resource: ResourceProvider

    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addUserUseCase = AddUserUseCase(repository, resource)
    }

    @Test
    fun `addUser should not call repository when user email already exist`() = runBlocking {
        // Arrange
        val user = User(name = "John Doe", email = "test@example.com", password = "password123")
        coEvery { repository.checkIfUserExist(user.email) } returns true

        // Act
        addUserUseCase(user)

        // Assert
        coVerify(exactly = 0) { repository.addUser(user) }
    }

    @Test
    fun `addUser should call repository with correct user`() = runBlocking {
        // Arrange
        val user = User(name = "John Doe", email = "test@example.com", password = "password123")
        coEvery { repository.checkIfUserExist(user.email) } returns false

        // Act
        addUserUseCase(user)

        // Assert
        coVerify(exactly = 1) { repository.addUser(user) }
    }
}