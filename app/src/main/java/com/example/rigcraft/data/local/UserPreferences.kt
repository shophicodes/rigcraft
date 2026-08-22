package com.example.rigcraft.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
        val SAVED_EMAIL_KEY = stringPreferencesKey("saved_email")
    }

    val isRememberMeEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: false
    }

    val savedEmail: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SAVED_EMAIL_KEY] ?: ""
    }

    suspend fun saveRememberMe(enabled: Boolean, email: String = "") {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = enabled
            preferences[SAVED_EMAIL_KEY] = if (enabled) email else ""
        }
    }
}