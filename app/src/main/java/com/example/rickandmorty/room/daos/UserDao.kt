package com.example.rickandmorty.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.room.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email LIMIT 1")
    suspend fun findUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)
}
