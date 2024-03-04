package com.myapp.rickandmorty.core.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myapp.rickandmorty.core.App.Companion.userUUID
import com.myapp.rickandmorty.core.data.Person
import java.util.UUID

@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userUUID: UUID,
    val photo: String,
    val name: String,
    val age: Int,
    val address: String,
    val phoneNumber: String,
    val hobbies: String
) {
    companion object {
        fun Person.toEntity() = PersonEntity(
            userUUID = userUUID,
            photo = this.photo,
            name = this.name,
            age = this.age,
            address = this.address,
            phoneNumber = this.phoneNumber,
            hobbies = this.hobbies
        )
    }

    fun fromEntity() = Person(
        photo = photo,
        name = name,
        age = age,
        address = address,
        phoneNumber = phoneNumber,
        hobbies = hobbies
    )
}