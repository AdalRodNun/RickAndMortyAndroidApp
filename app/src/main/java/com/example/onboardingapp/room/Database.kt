package com.example.onboardingapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onboardingapp.room.daos.PersonDao
import com.example.onboardingapp.room.daos.UserDao
import com.example.onboardingapp.room.models.Person
import com.example.onboardingapp.room.models.User

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