package com.myapp.rickandmorty.core

import android.app.Application
import androidx.room.Room
import com.myapp.rickandmorty.core.service.APIService.getAPIRetrofit
import com.myapp.rickandmorty.core.room.AppDatabase
import com.myapp.rickandmorty.utils.Constants.ROOM_DATABASE_NAME
import retrofit2.Retrofit

class App : Application() {

    companion object {
        lateinit var room: AppDatabase
            private set

        lateinit var retrofit: Retrofit
            private set
    }

    override fun onCreate() {
        super.onCreate()

        room = Room
            .databaseBuilder(this, AppDatabase::class.java, ROOM_DATABASE_NAME)
            .build()

        retrofit = getAPIRetrofit()
    }
}