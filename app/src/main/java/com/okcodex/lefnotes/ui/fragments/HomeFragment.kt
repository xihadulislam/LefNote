package com.okcodex.lefnotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.okcodex.lefnotes.R
import com.okcodex.lefnotes.db.DatabaseNote
import com.okcodex.lefnotes.ui.Adapters.NoteAdapter
import com.okcodex.lefnotes.utils.CustomBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : CustomBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        recylr_view.setHasFixedSize(true)
        recylr_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        launch {

            context?.let {

                val notes = DatabaseNote(it).getNoteDao().getallNotes()

                recylr_view.adapter = NoteAdapter(notes)


            }

        }


        butn_add.setOnClickListener {

            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)

        }
    }


}
