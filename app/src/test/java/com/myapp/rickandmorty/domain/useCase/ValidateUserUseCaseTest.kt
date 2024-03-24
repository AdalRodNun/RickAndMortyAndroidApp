package com.myapp.rickandmorty.domain.useCase

import com.google.common.truth.Truth.assertThat
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.data.UserUUID
import com.myapp.rickandmorty.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mindrot.jbcrypt.BCrypt

class ValidateUserUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: UserRepository

    @RelaxedMockK
    private lateinit var userUUID: UserUUID

    private lateinit var validateUserUseCase: ValidateUserUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        validateUserUseCase = ValidateUserUseCase(repository, userUUID)
    }

    /*@Test
    fun `When user was not found`() = runBlocking {
        //Given
        coEvery { repository.getUserByEmail(any()) } returns null

        //When
        validateUserUseCase("test@example.com", "password123")

        //Then
        coVerify(exactly = 1) { repository.getUserByEmail(any()) }
    }*/

    @Test
    fun `validateUser should return false when user does not exist`() = runBlocking {
        // Given
        coEvery { repository.getUserByEmail(any()) } returns null

        // When
        val result = validateUserUseCase("test@example.com", "password123")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `validateUser should return false when user exist and password is invalid`() = runBlocking {
        // Given
        val hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt())
        val user = User(name = "John Doe", email = "test@example.com", password = hashedPassword)
        coEvery { repository.getUserByEmail("test@example.com") } returns user

        // When
        val result = validateUserUseCase("test@example.com", "invalidPassword")

        // Then
        assertThat(result).isFalse()
    }

    @Test
    fun `validateUser should return true when user exists and password is valid`() = runBlocking {
        // Given
        val hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt())
        val user = User(name = "John Doe", email = "test@example.com", password = hashedPassword)
        coEvery { repository.getUserByEmail("test@example.com") } returns user

        // When
        val result = validateUserUseCase("test@example.com", "password123")

        // Then
        assertThat(result).isTrue()
    }
}