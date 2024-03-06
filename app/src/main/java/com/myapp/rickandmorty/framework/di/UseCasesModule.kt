package com.myapp.rickandmorty.framework.di

import com.myapp.rickandmorty.core.repository.UserRepository
import com.myapp.rickandmorty.core.useCase.AddUser
import com.myapp.rickandmorty.core.useCase.GetUserByEmail
import com.myapp.rickandmorty.core.useCase.ValidateUser
import com.myapp.rickandmorty.framework.UserUseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(repository: UserRepository) = UserUseCases(
        AddUser(repository),
        GetUserByEmail(repository),
        ValidateUser(repository)
    )
}