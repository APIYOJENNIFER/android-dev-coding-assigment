package com.ensibuuko.android_dev_coding_assigment.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "posts_preferences")

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {
     val preferencesFlow = context.dataStore.data
         .map {

         }
}