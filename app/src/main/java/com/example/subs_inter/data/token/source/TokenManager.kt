package com.example.subs_inter.data.token.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val userLogin = booleanPreferencesKey("user_login")
    private val userName = stringPreferencesKey("user_name")
    private val userEmail = stringPreferencesKey("user_email")

    suspend fun setUserLogin() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[userLogin] = true
        }
    }

    fun isUserLogin(): Flow<Boolean> = dataStore.data.map {
        it[userLogin] ?: false
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[userName] = name
        }
    }

    fun getUserName() = dataStore.data.map {
        it[userName] ?: ""
    }

    suspend fun setUserEmail(email: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[userEmail] = email
        }
    }

    fun getUserEmail() = dataStore.data.map {
        it[userEmail] ?: ""
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }
}