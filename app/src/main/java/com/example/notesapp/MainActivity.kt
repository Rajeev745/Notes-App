package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteInfo
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelFactory

class MainActivity : AppCompatActivity(), NotesClickInterface, ExchangeData {

    lateinit var notesViewModel: NotesViewModel
    private var noteInfo = NoteInfo("","","",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesViewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(application)
        ).get(NotesViewModel::class.java)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, NotesFragment())
            .commit()

    }


    override fun onDeleteIcon(note: Note) {
        notesViewModel.deleteNote(note)
        Toast.makeText(this, "${note.notesTitle} Deleted Successfully", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, NotesDescriptionFragment())
            .commit()
    }

    override fun getData(): NoteInfo {
        return noteInfo
    }

    override fun setData(noteInfo: NoteInfo) {
        this.noteInfo = noteInfo
    }

}