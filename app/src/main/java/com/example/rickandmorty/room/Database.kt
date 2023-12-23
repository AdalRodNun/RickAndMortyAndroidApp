package com.example.rickandmorty.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.room.daos.PersonDao
import com.example.rickandmorty.room.daos.UserDao
import com.example.rickandmorty.room.models.Person
import com.example.rickandmorty.room.models.User

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