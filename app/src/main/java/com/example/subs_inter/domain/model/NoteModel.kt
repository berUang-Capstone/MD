package com.example.subs_inter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    var id: Int? = null,
    var title: String,
    var category: String,
    var item: String,
    var amount: String,
    var quantity: String,
    var date: String
): Parcelable
