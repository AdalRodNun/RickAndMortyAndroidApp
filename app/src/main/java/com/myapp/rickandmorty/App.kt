package com.myapp.rickandmorty

import android.app.Application
import androidx.room.Room
import com.myapp.rickandmorty.room.AppDatabase

class App : Application() {

    companion object {
        lateinit var room: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        room = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()
    }
}