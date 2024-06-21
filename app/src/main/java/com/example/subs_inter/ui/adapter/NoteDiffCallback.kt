package com.example.subs_inter.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.subs_inter.domain.model.NoteModel

class NoteDiffCallback(private val oldNoteListEntity: List<NoteModel>, private val newNoteListEntity: List<NoteModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteListEntity.size
    override fun getNewListSize(): Int = newNoteListEntity.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteListEntity[oldItemPosition].id == newNoteListEntity[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteListEntity[oldItemPosition]
        val newNote = newNoteListEntity[newItemPosition]
        return oldNote.title == newNote.title &&
                oldNote.category == newNote.category &&
                oldNote.item == newNote.item &&
                oldNote.amount == newNote.amount &&
                oldNote.quantity == newNote.quantity &&
                oldNote.date == newNote.date
    }
}
