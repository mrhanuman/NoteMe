package com.androhanu.noteme

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.*

class EditAddNoteActivity : AppCompatActivity() {
    private lateinit var edtTitle : EditText
    private lateinit var edtDescription : EditText
    private lateinit var addUpdateButton: Button
    private lateinit var viewModal: NoteViewModal
    var noteId = -1
    @SuppressLint("SetTextI18n", "NewApi", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_note)

        edtTitle = findViewById(R.id.IdEdtTitle)
        edtDescription = findViewById(R.id.idEdtDescription)
        addUpdateButton = findViewById(R.id.addUpdateButton)

        viewModal = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModal::class.java]



        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId",-1)
            addUpdateButton.text = "Update"
            edtTitle.setText(noteTitle)
            edtDescription.setText(noteDesc)
        }else{
            addUpdateButton.text = "Add"

        }

        addUpdateButton.setOnClickListener {
            val noteTitle = edtTitle.text.toString()
            val noteDescription = edtDescription.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd,MMM,yyyy - hh:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDescription, currentDate)
                    updateNote.id = noteId
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this@EditAddNoteActivity, "Notes updated !", Toast.LENGTH_SHORT)
                        .show()

                }
            }   else if ((noteTitle.isNotEmpty() && noteDescription.isNotEmpty())){
                        val sdf = SimpleDateFormat("dd,MMM,yyyy - hh:mm")
                        val currentDate : String = sdf.format(Date())
                        viewModal.addNote(Note(noteTitle, noteDescription, currentDate))
                        Toast.makeText(this@EditAddNoteActivity, "Notes Added !", Toast.LENGTH_SHORT).show()
                }

            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }
}