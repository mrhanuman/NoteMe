package com.androhanu.noteme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickInterface,
    NoteRVAdapter.NoteClickDeleteInterface {
    lateinit var notesRV : RecyclerView
    private lateinit var floatingActionButton : FloatingActionButton
    private lateinit var viewModal: NoteViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.idRVNotes)
        floatingActionButton = findViewById(R.id.idFABAddButton)
        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this,this,this)
        notesRV.adapter = noteRVAdapter
        viewModal = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModal::class.java]
        viewModal.allNote.observe(this, Observer {
            it?.let {
                noteRVAdapter.updateList(it)
            }
        })

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity,EditAddNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,EditAddNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteId",note.id)
        startActivity(intent)
        this.finish()

    }

    override fun onDeleteIconClicked(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} deleted", Toast.LENGTH_SHORT).show()

    }
}