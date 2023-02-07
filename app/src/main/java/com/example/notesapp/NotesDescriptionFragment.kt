package com.example.notesapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.Note
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class NotesDescriptionFragment : Fragment() {

    lateinit var noteTitleEdt: EditText
    lateinit var noteDescEdt: EditText
    lateinit var saveButton: Button
    lateinit var notesViewModel: NotesViewModel
    lateinit var exchangeData: ExchangeData

    private var noteType = ""
    private var noteTitle = ""
    private var noteDescription = ""
    private var noteId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_description, container, false)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel =
            ViewModelProvider(this, NotesViewModelFactory(requireActivity().application)).get(
                NotesViewModel::class.java
            )

        exchangeData = activity as ExchangeData
        noteTitleEdt = view.findViewById(R.id.edit_note_title)
        noteDescEdt = view.findViewById(R.id.notes_description)
        saveButton = view.findViewById(R.id.save_button)

        noteType = exchangeData.getData().noteType
        noteTitle = exchangeData.getData().noteTitle
        noteDescription = exchangeData.getData().noteDesc
        noteId = exchangeData.getData().id

        if (noteType == "Edit") {

            noteTitleEdt.setText(noteTitle)
            noteDescEdt.setText(noteDescription)
            saveButton.text = "Update Text"
        }

        saveButton.setOnClickListener {
            noteTitle = noteTitleEdt.text.toString()
            noteDescription = noteDescEdt.text.toString()

            if (noteType == "Edit") {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteId
                    notesViewModel.updateNote(updatedNote)

                    Toast.makeText(
                        requireContext().applicationContext,
                        "$noteTitle updated successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())

                    notesViewModel.insertNote(Note(noteTitle, noteDescription, currentDateAndTime))

                    Toast.makeText(
                        requireContext().applicationContext,
                        "$noteTitle added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

}