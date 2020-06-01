package com.okcodex.lefnotes.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

import com.okcodex.lefnotes.R
import com.okcodex.lefnotes.db.DatabaseNote
import com.okcodex.lefnotes.db.Note
import com.okcodex.lefnotes.utils.CustomBaseFragment
import com.okcodex.lefnotes.utils.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : CustomBaseFragment() {


    private  var mnote : Note? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {

            mnote = AddNoteFragmentArgs.fromBundle(it).note
            editText_title.setText(mnote?.title)
            editText_description.setText(mnote?.note)
        }


        butn_save.setOnClickListener { view ->

            val  notetitle = editText_title.text.toString().trim()
            val notdescription = editText_description.text.toString().trim()

            if (notetitle.isEmpty()){
                editText_title.error = "Title Required"
                return@setOnClickListener
            }
            if (notdescription.isEmpty()){
                editText_description.error = " Description Required"
                return@setOnClickListener
            }


            launch {


                context?.let {

                    var note = Note (notetitle,notdescription)

                    if (mnote==null){
                        DatabaseNote(it).getNoteDao().addNote(note)
                        it.toast("Note Saved")
                    }
                    else
                    {
                        note.id = mnote!!.id
                        DatabaseNote(it).getNoteDao().updateNote(note)
                        it.toast("Note Updated")
                    }

//                    val action = AddNoteFragmentDirections.actionSaveNote()
//                    Navigation.findNavController(view).navigate(action)

                    activity?.onBackPressed()

                }


            }

        }


    }


    private  fun deleteNote (){
        AlertDialog.Builder(context).apply {

            setTitle("Are you sure ?")
            setMessage("you can't undo this note")
            setPositiveButton("Yes"){ _,_ ->

                context?.let {
                    launch {
                        DatabaseNote(it).getNoteDao().deleteNote(mnote!!)
//                        val action = AddNoteFragmentDirections.actionSaveNote()
//                        Navigation.findNavController(requireView()).navigate(action)

                        it.toast("Note Deleted")
                        activity?.onBackPressed()

                    }

                }
            }

            setNegativeButton("No"){_,_->
            }
        }.create().show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.menu_delete -> if (mnote!=null)  deleteNote() else context?.toast("Can't delete ")

        }

        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

}
