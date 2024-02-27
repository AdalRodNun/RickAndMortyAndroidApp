package com.myapp.rickandmorty.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapp.rickandmorty.room.daos.PersonDao
import com.myapp.rickandmorty.room.daos.UserDao
import com.myapp.rickandmorty.room.models.Person
import com.myapp.rickandmorty.room.models.User

@Database(
    entities = [
        User::class,
        Person::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun personDao(): PersonDao
}