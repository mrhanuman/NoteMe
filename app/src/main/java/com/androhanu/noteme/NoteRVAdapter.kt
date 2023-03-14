package com.androhanu.noteme

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    interface NoteClickDeleteInterface {
        fun onDeleteIconClicked(note: Note)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = allNote[position]
        holder.noteTV.text = currentPosition.noteTitle
        holder.timeTV.text = "last updated : "+(currentPosition.timeStamp)

        holder.IVDelete.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClicked(currentPosition)
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(currentPosition)
        }

    }

    override fun getItemCount(): Int {
        return allNote.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>){
        allNote.clear()
        allNote.addAll(newList)
        notifyDataSetChanged()
    }




    private val allNote = ArrayList<Note>()
    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNoteTitle)
        val timeTV = itemView.findViewById<TextView>(R.id.idTVTimeStamp)
        val IVDelete = itemView.findViewById<ImageView>(R.id.IdIVDelete)

    }


}