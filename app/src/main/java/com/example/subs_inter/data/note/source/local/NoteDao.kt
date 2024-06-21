package com.example.subs_inter.data.note.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.subs_inter.data.note.source.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Query("SELECT * from noteentity ORDER BY id ASC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("Select * from noteentity where category = :category")
    fun getNotesByCategory(category: String): Flow<List<NoteEntity>>

    @Query("DELETE FROM noteentity WHERE category = :category")
    suspend fun deleteAllByCategory(category: String)
}