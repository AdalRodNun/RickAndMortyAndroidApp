package com.myapp.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.myapp.rickandmorty.core.room.RoomDatabase
import com.myapp.rickandmorty.utils.Constants.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, RoomDatabase::class.java, ROOM_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUserDao(db: RoomDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideCharacterDao(db: RoomDatabase) = db.characterDao()

}