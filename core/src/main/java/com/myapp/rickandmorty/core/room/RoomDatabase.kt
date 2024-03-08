package com.myapp.rickandmorty.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.rickandmorty.core.room.daos.CharacterDao
import com.myapp.rickandmorty.core.room.daos.UserDao
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.core.room.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CharacterEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class RoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun characterDao(): CharacterDao
}