package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.ExchangeData
import com.example.notesapp.NotesClickInterface
import com.example.notesapp.R
import com.example.notesapp.database.Note
import com.example.notesapp.database.NoteInfo

class NotesAdapter(
    val context: Context,
    val notesClickInterface: NotesClickInterface,
    val exchangeData: ExchangeData
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesTitle = itemView.findViewById<TextView>(R.id.notes_title)
        val notesDateAndTime = itemView.findViewById<TextView>(R.id.time_data)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_button)
        val cardView = itemView.findViewById<CardView>(R.id.recycler_card)
    }

    private var allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val red = (Math.random() * 256).toInt()
        val green = (Math.random() * 256).toInt()
        val blue = (Math.random() * 256).toInt()

        val color: Int = Color.rgb(red, green, blue)

        holder.cardView.setBackgroundColor(color)
        holder.notesTitle.text = allNotes[position].notesTitle
        holder.notesDateAndTime.text = allNotes[position].timeStamp

        holder.deleteButton.setOnClickListener {
            notesClickInterface.onDeleteIcon(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            notesClickInterface.onNoteClick(allNotes[position])
            val notedata = NoteInfo(
                "Edit",
                allNotes.get(position).notesTitle,
                allNotes.get(position).notesDescription,
                allNotes.get(position).id
            )
            exchangeData.setData(notedata)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    interface SendNoteData {
        fun sendNotesData(
            noteType: String,
            noteTitle: String,
            noteDescription: String,
            noteId: Int
        )
    }
}

