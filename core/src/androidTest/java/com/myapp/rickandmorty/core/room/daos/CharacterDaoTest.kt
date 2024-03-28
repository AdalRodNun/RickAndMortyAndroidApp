package com.myapp.rickandmorty.core.room.daos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.myapp.rickandmorty.core.room.RoomDatabase
import com.myapp.rickandmorty.core.room.entities.CharacterEntity
import com.myapp.rickandmorty.core.room.entities.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDaoTest {

    private lateinit var database: RoomDatabase
    private lateinit var characterDao: CharacterDao

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context,
            RoomDatabase::class.java
        ).allowMainThreadQueries().build()

        characterDao = database.characterDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addCharacterAndGetAllCharactersByUserUUID() = runBlocking {
        val userUUID = UUID.randomUUID()
        val character = CharacterEntity(
            id = 1,
            userUUID = userUUID,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth",
            location = "Earth",
            image = null,
            created = null
        )
        characterDao.addCharacter(character)

        val characters = characterDao.getAllCharactersByUserUUID(userUUID)

        assertThat(characters).contains(character)
    }

    @Test
    fun addCharacterAndGetAllCharactersByUserUUIDAndReturnNothing() = runBlocking {
        val userUUID = UUID.randomUUID()
        val character = CharacterEntity(
            id = 1,
            userUUID = userUUID,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            origin = "Earth",
            location = "Earth",
            image = null,
            created = null
        )
        val anotherUserUUID = UUID.randomUUID()
        characterDao.addCharacter(character)

        val characters = characterDao.getAllCharactersByUserUUID(anotherUserUUID)

        assertThat(characters).isEmpty()
    }
}