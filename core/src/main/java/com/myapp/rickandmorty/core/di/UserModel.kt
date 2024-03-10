package com.myapp.rickandmorty.core.di

import com.myapp.rickandmorty.core.data.UserUUID
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserModel {

    @Singleton
    @Provides
    fun provideDataInstance(): UserUUID {
        return UserUUID()
    }
}