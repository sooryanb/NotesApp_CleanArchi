package com.example.notecleanarchi.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.notecleanarchi.R
import com.example.notecleanarchi.framework.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NoteFragment : Fragment() {

    private var noteId = 0L

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    private lateinit var checkButton: FloatingActionButton
    private lateinit var titleView: EditText
    private lateinit var contentView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        checkButton = view.findViewById<FloatingActionButton>(R.id.checkButton)
        titleView = view.findViewById<EditText>(R.id.titleView)
        contentView = view.findViewById<EditText>(R.id.contentView)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        if (noteId != 0L){
            viewModel.getNote(noteId)
        }

        checkButton.setOnClickListener {
            if(titleView.text.toString() != "" || contentView.text.toString() != ""){
                val time = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if(currentNote.id == 0L) currentNote.creationTime = time
                viewModel.saveNote(currentNote)
            }else{
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if(it) {
                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(titleView).popBackStack()
            }else{
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note->
            note?.let {
                currentNote = it
                titleView.setText(it.title)
                contentView.setText(it.content)
            }
        })

    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote -> {
                if (context != null && noteId != 0L){
                    AlertDialog.Builder(context).setTitle("Delete Note").setMessage("Are you sure about this?")
                        .setPositiveButton("Yes") {  dialodInterface, i -> viewModel.deleteNote(currentNote) }
                        .setNegativeButton("Cancel") {  dialodInterface, i -> }
                        .create()
                        .show()
                }
            }
        }
        return true
    }

}