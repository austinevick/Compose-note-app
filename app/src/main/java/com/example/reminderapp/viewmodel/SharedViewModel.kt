package com.example.reminderapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.compose.NoteEvent
import com.example.reminderapp.compose.NoteState
import com.example.reminderapp.database.NoteEntity
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: NoteRepository
):ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state:StateFlow<NoteState> = _state.asStateFlow()

    private val _noteListState = MutableStateFlow<List<NoteEntity>>(emptyList())
    val noteListState:StateFlow<List<NoteEntity>> = _noteListState.asStateFlow()

    private val _selectedPriority = mutableStateOf(Priority.LOW)
    val selectedPriority:MutableState<Priority> = _selectedPriority

    private val _selectedNotes = mutableStateListOf<Int>()
    val selectedNotes: SnapshotStateList<Int> = _selectedNotes



    init {
        viewModelScope.launch {
        repository.getAllNotes.collect{
            _noteListState.value = it
        }
        }
    }

    fun onEvent(event: NoteEvent,alarmTime:String? = null){
        when(event){
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.note)
                }
            }
            NoteEvent.SaveNote -> {
                val title = _state.value.title
                val desc = _state.value.description
                val note = NoteEntity(title = title,
                    description = desc,
                    priority = _selectedPriority.value,
                    alarmDate =alarmTime,
                    date = Date())
                viewModelScope.launch {
                    repository.addNote(note)
                    Log.d("note",note.toString())
                }
            }
            is NoteEvent.SetDescription ->{
                _state.update { it.copy(description = event.description) }
            }
            is NoteEvent.SetTitle -> {
                _state.update { it.copy(title = event.title) }
            }

            is NoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    repository.deleteNotes(event.id)
                    selectedNotes.clear()
                }
            }

            is NoteEvent.IsAdding -> {
              event.let {
                  it.copy(value = it.value)
              }
            }
        }
    }
}