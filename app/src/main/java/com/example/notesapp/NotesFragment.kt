package com.example.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.NoteInfo
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {

    lateinit var viewModel: NotesViewModel
    lateinit var button: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var notesAdapter: NotesAdapter
    lateinit var notesClickInterface: NotesClickInterface
    lateinit var exchangeData: ExchangeData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(requireActivity().application)
        )[NotesViewModel::class.java]

        notesClickInterface = activity as NotesClickInterface
        exchangeData = activity as ExchangeData

        button = view.findViewById(R.id.floating_button)
        recyclerView = view.findViewById(R.id.recycler_view)
        notesAdapter =
            context?.let {
                NotesAdapter(
                    it.applicationContext,
                    notesClickInterface,
                    exchangeData
                )
            }!!

        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        recyclerView.adapter = notesAdapter

        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            notesAdapter.updateList(it)
        })

        button.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.add(R.id.fragment_container, NotesDescriptionFragment())?.commit()
        }
    }
}