package com.okcodex.lefnotes.db

import androidx.room.*

@Dao
interface NoteDao {


    @Insert
   suspend fun addNote(note : Note)

    @Query( " SELECT * FROM note ORDER BY id DESC ")
   suspend fun  getallNotes () : List<Note>

    @Update
    suspend fun updateNote(note : Note)

    @Delete
    suspend fun deleteNote(note: Note)


}