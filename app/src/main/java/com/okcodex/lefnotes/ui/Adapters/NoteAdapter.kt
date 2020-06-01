package com.okcodex.lefnotes.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.okcodex.lefnotes.R
import com.okcodex.lefnotes.db.Note
import com.okcodex.lefnotes.ui.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_layout.view.*

class NoteAdapter(val noteslist : List<Note>)  : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return  NoteViewHolder(view)
    }

    override fun getItemCount() = noteslist.size
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val notes = noteslist[position]

        holder.itemView.text_title.text= notes.title
        holder.itemView.text_description.text= notes.note

        holder.itemView.setOnClickListener {

            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes
            Navigation.findNavController(it).navigate(action)

        }



    }


    class  NoteViewHolder(view : View) : RecyclerView.ViewHolder (view)


}