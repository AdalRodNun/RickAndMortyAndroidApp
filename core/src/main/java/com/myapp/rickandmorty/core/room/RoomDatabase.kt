package com.myapp.rickandmorty.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.rickandmorty.core.room.daos.PersonDao
import com.myapp.rickandmorty.core.room.daos.UserDao
import com.myapp.rickandmorty.core.room.models.PersonEntity
import com.myapp.rickandmorty.core.room.models.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PersonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class RoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun personDao(): PersonDao
}