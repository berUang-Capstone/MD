package com.example.subs_inter.di

import android.content.Context
import androidx.room.Room
import com.example.subs_inter.data.note.source.local.NoteDao
import com.example.subs_inter.data.note.source.local.NoteRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteRoomDatabase(@ApplicationContext context: Context): NoteRoomDatabase {
        return Room.databaseBuilder(
            context,
            NoteRoomDatabase::class.java, "note_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(database: NoteRoomDatabase): NoteDao {
        return database.noteDao()
    }
}