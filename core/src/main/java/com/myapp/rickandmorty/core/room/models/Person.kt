package com.myapp.rickandmorty.core.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val photo: String,
    val name: String,
    val age: Int,
    val address: String,
    val phoneNumber: String,
    val hobbies: String
)