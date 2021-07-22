package com.example.notecleanarchi.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notecleanarchi.R
import com.example.notecleanarchi.framework.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment(), ListAction {

    private val noteListAdapter = NoteListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel


    private lateinit var notesListView: RecyclerView
    private lateinit var loadingView : ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesListView = view.findViewById(R.id.noteListView)
        loadingView = view.findViewById<ProgressBar>(R.id.loadingView)

        notesListView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }

        val addNote = view?.findViewById<FloatingActionButton>(R.id.addNotes)

        addNote.setOnClickListener {
            Log.d("CLICKED", "YEs")
            goToNoteDetails()
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner, Observer { notesList->
            loadingView?.isGone = true
            noteListAdapter.updateNotes(notesList.sortedByDescending { it.updateTime })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L){
        val action = ListFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(requireView().findViewById(R.id.noteListView)).navigate(action)
    }

    override fun onClick(id: Long) {
       goToNoteDetails(id)
    }

}