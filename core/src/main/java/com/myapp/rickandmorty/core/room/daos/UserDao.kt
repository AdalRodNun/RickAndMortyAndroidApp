package com.myapp.rickandmorty.core.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.myapp.rickandmorty.core.room.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = IGNORE)
    suspend fun addUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

}
