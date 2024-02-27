package com.myapp.rickandmorty.core.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)