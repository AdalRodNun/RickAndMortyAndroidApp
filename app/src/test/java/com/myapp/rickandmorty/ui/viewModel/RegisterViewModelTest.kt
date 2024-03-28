package com.myapp.rickandmorty.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.myapp.rickandmorty.core.data.User
import com.myapp.rickandmorty.core.room.DBResponse
import com.myapp.rickandmorty.domain.useCase.AddUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest{

    @RelaxedMockK
    private lateinit var addUserUseCase: AddUserUseCase

    private lateinit var registerViewModel: RegisterViewModel

    //Regla para permitir pruebas con LiveData
    @get:Rule
    var rule:InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        registerViewModel = RegisterViewModel(addUserUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveUser should post true to saved LiveData when addUserUseCase returns Success`() = runTest {
        // Arrange
        val user = User(
            name = "Chelsea Goodwin",
            email = "anne.bauer@example.com",
            password = "commune"
        )
        coEvery { addUserUseCase(user) } returns DBResponse.Success()

        // Act
        registerViewModel.saveUser(user)

        // Assert
        assertThat(registerViewModel.saved.value).isTrue()
    }

    @Test
    fun `saveUser should set message LiveData when addUserUseCase returns Error`() = runTest {
        // Arrange
        val user = User(
            name = "Chelsea Goodwin",
            email = "anne.bauer@example.com",
            password = "commune"
        )
        val errorMessage = "User already exists"
        coEvery { addUserUseCase(user) } returns DBResponse.Error(errorMessage)

        // Act
        registerViewModel.saveUser(user)

        // Assert
        assertThat(registerViewModel.message.value).isEqualTo(errorMessage)
    }
}