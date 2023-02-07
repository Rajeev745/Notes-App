package com.example.notesapp.database

data class NoteInfo(
    val noteType: String,
    val noteTitle: String,
    val noteDesc: String,
    val id: Int
) {
}