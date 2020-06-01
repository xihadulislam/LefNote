package com.okcodex.lefnotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (
    entities = [Note::class],
    version = 1
)
abstract class DatabaseNote : RoomDatabase() {

    abstract  fun getNoteDao () : NoteDao

    companion object {

        @Volatile
        private  var instance : DatabaseNote ? = null
        private  val lockval = Any()


        operator  fun invoke (context : Context) = instance ?: synchronized(lockval){
            instance?: buildDatabase(context).also {
                instance = it
            }

        }


        private  fun buildDatabase (context : Context)  = Room.databaseBuilder(
            context.applicationContext,
            DatabaseNote::class.java,
            "notedatabase"

        ).build()




    }



}