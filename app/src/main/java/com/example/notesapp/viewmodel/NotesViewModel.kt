package com.example.notesapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteInfo
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : ViewModel() {

    var allNotes: LiveData<List<Note>>
    var notesRepository: NotesRepository
    var noteInfo = MutableLiveData<NoteInfo>()

    init {
        val dao = NotesDatabase.getDatabase(application.applicationContext).getNotesDao()
        notesRepository = NotesRepository(dao)
        allNotes = notesRepository.allNotes

    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.update(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.insert(note)
    }

}