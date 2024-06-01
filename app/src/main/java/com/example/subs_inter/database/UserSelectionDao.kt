package com.example.subs_inter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserSelectionDao {
    @Insert
    suspend fun insertUserSelection(userSelection: UserSelection)

    @Query("SELECT * FROM user_selections")
    suspend fun getAllSelections(): List<UserSelection>
}