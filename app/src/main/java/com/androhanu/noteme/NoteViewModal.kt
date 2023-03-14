package com.androhanu.noteme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModal(application: Application):AndroidViewModel(application) {
     val allNote : LiveData<List<Note>>
     val repository : NoteRepository


    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNote = repository.allNotes
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun addNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
}