package com.myapp.rickandmorty.core

import android.app.Application
import androidx.room.Room
import com.myapp.rickandmorty.core.framework.retrofit.APIService.getAPIRetrofit
import com.myapp.rickandmorty.core.framework.room.RoomDatabase
import com.myapp.rickandmorty.utils.Constants.ROOM_DATABASE_NAME
import retrofit2.Retrofit
import java.util.UUID

class App : Application() {

    companion object {
        lateinit var userUUID: UUID
            private set

        lateinit var room: RoomDatabase
            private set

        lateinit var retrofit: Retrofit
            private set

        fun setUserUUID(userID: UUID) {
            userUUID = userID
        }
    }

    override fun onCreate() {
        super.onCreate()

        room = Room
            .databaseBuilder(this, RoomDatabase::class.java, ROOM_DATABASE_NAME)
            .build()

        retrofit = getAPIRetrofit()

    }
}