package com.example.datatabledemo.database

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    val getAllNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()
    val sortByLowPriority: Flow<List<NoteEntity>> = noteDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<NoteEntity>> = noteDao.sortByHighPriority()

    fun getNoteById(noteId: Int): Flow<NoteEntity> {
        return noteDao.getNoteById(noteId)
    }

    suspend fun addNote(noteEntity: NoteEntity) {
        noteDao.addNote(noteEntity)
    }

    suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.updateTask(noteEntity)
    }

    suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity)
    }

    suspend fun deleteNotes(id:List<Int>) {
        noteDao.deleteNotes(id)
    }


    suspend fun deleteAllNote() {
        noteDao.deleteAllNotes()
    }
}