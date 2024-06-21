package com.example.subs_inter.data.note.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.subs_inter.data.note.source.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}