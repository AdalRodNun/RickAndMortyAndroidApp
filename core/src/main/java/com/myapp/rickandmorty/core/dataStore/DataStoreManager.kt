package com.myapp.rickandmorty.core.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.myapp.rickandmorty.core.dataStore.PreferencesKeys.SESSION_ID
import com.myapp.rickandmorty.utils.Constants.DATASTORE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreManager @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val dataStore = appContext.dataStore

    suspend fun saveSessionID(sessionID: UUID) {
        dataStore.edit { it[SESSION_ID] = sessionID.toString() }
    }

    fun getSessionID() = dataStore.data.map {
        val uuidString = it[SESSION_ID]
        UUID.fromString(uuidString) ?: null
    }

    suspend fun deleteSessionID() {
        dataStore.edit { it.remove(SESSION_ID) }
    }
}