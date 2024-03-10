package com.myapp.rickandmorty.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapp.rickandmorty.core.data.User
import java.util.UUID

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey
    val id: UUID,
    val name: String,
    val email: String,
    val password: String
) {
    companion object {
        fun User.toEntity() = UserEntity(
            id = this.uuid,
            name = this.name,
            email = this.email,
            password = this.password
        )
    }

    fun fromEntity() = User(
        uuid = id,
        name = name,
        email = email,
        password = password
    )
}