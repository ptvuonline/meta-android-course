package com.drkumo.littlelemon.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.drkumo.littlelemon.model.Person
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DataStorePersonRepository(private val context: Context) : PersonRepository {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
        private val preferencesKey = stringPreferencesKey("person_data")

    }

    override suspend fun savePerson(person: Person) {
        val jsonString = Json.encodeToString(serializer = Person.serializer(), person)

        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = jsonString
        }
    }

    override suspend fun readPerson(): Person? {
        val jsonString = context.dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey("person_data")] ?: ""
            }.first()

        return if (jsonString.isNotBlank()) {
            Json.decodeFromString(Person.serializer(), jsonString)
        } else {
            null
        }

    }

    override suspend fun clearPerson() {
        context.dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }
}