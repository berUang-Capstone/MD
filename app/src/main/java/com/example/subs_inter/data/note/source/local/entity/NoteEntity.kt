package com.example.subs_inter.data.note.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "income")
    var title: String? = null,

    @ColumnInfo(name = "category")
    var category: String? = null,

    @ColumnInfo(name = "item")
    var item: String? = null,

    @ColumnInfo(name = "amount")
    var amount: String? = null,

    @ColumnInfo(name = "quantity")
    var quantity: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
)