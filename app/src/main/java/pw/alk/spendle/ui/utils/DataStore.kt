package pw.alk.spendle.ui.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val TOKEN_NAME = "AUTH_TOKEN"
private const val PREF_NAME = "user_settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

class TokenDataStore(private val context: Context) {
    suspend fun saveToken(value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(TOKEN_NAME)] = value
        }
    }

    suspend fun getToken(): String? =
        context.dataStore.data.first()[stringPreferencesKey(TOKEN_NAME)]
}