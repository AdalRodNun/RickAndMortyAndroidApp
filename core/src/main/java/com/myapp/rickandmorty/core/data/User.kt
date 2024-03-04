package com.myapp.rickandmorty.core.data

import java.util.UUID

data class User (
    val uuid: UUID = UUID.fromString(DEFAULT_UUID),
    val name: String,
    val email: String,
    val password: String
) {
    companion object{
        const val DEFAULT_UUID = "00000000-0000-0000-0000-000000000000"
    }
}