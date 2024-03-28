package com.myapp.rickandmorty.core.room.daos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.myapp.rickandmorty.core.room.RoomDatabase
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
class UserDaoTest {

    private lateinit var database: RoomDatabase
    private lateinit var userDao: UserDao

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context,
            RoomDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addUserAndGetByEmail() = runBlocking {
        val user = UserEntity(UUID.randomUUID(), "John Doe", "john@example.com", "password123")

        userDao.addUser(user)
        val retrievedUser = userDao.getUserByEmail(user.email)

        assertThat(retrievedUser).isEqualTo(user)
    }

    @Test
    fun checkIfUserExist() = runBlocking {
        val user = UserEntity(UUID.randomUUID(), "John Doe", "john@example.com", "password123")

        userDao.addUser(user)
        val userExists = userDao.checkIfUserExist(user.email)

        assertThat(userExists).isTrue()
    }
}