package com.example.subs_inter.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_selections")
data class UserSelection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val category: String
)