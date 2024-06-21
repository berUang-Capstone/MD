package com.example.subs_inter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_setting")

@Module
@InstallIn(SingletonComponent::class)
object UserTokenManager {
    @Provides
    @Singleton
    fun provideUserToken(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}