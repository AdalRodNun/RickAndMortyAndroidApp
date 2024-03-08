package com.myapp.rickandmorty.di

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