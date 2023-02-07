package com.example.notesapp

import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteInfo

interface NotesClickInterface {
    fun onDeleteIcon(note: Note)
    fun onNoteClick(note: Note)
}

interface ExchangeData {
    fun getData(): NoteInfo
    fun setData(noteInfo: NoteInfo)
}

