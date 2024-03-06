package com.myapp.rickandmorty.framework.di

import com.myapp.rickandmorty.core.repository.UserRepository
import com.myapp.rickandmorty.framework.RoomUserDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository() = UserRepository(RoomUserDataSource())
}