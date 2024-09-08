package com.example.reminderapp.compose

import com.example.reminderapp.database.NoteEntity

sealed interface NoteEvent {
    data object SaveNote : NoteEvent
    data class DeleteNote(val note: NoteEntity) : NoteEvent
    data class DeleteNotes(val id: List<Int>) : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetDescription(val description: String) : NoteEvent
    data class IsAdding(val value: Boolean) : NoteEvent
}