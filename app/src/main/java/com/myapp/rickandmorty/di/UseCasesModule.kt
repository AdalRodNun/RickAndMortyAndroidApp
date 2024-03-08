/*package com.myapp.rickandmorty.di

import com.myapp.rickandmorty.core.repository.UserRepository
import com.myapp.rickandmorty.domain.useCase.AddUser
import com.myapp.rickandmorty.domain.useCase.GetUserByEmail
import com.myapp.rickandmorty.domain.useCase.ValidateUser
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
}*/