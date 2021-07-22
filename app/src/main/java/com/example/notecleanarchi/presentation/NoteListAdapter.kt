package com.example.notecleanarchi.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.notecleanarchi.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteListAdapter(var notes: ArrayList<Note>, val actions: ListAction): RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val layout = view.findViewById<CardView>(R.id.noteLayout)
        private val noteTitle = view.findViewById<TextView>(R.id.title)
        private val noteContent = view.findViewById<TextView>(R.id.content)
        private val noteDate = view.findViewById<TextView>(R.id.date)

        fun bind(note: Note){
            noteTitle.text = note.title
            noteContent.text = note.content

            layout.setOnClickListener {
                actions.onClick(note.id)
            }

            val sdf = SimpleDateFormat("MMM dd HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last Updated: ${sdf.format(resultDate)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}