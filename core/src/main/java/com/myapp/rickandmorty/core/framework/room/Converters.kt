package com.myapp.rickandmorty.core.framework.room

import androidx.room.TypeConverter
import com.myapp.rickandmorty.core.data.User
import java.util.UUID

class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        if ( uuid.toString() == User.DEFAULT_UUID )
            return UUID.randomUUID().toString()

        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID {
        string?.let {
            return UUID.fromString(string)
        }
        return UUID.fromString(User.DEFAULT_UUID)
    }

}