package com.example.notesapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_table")

class Note(
    @ColumnInfo(name = "title") val notesTitle: String,
    @ColumnInfo(name = "description") val notesDescription: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: String
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}