package com.example.simplenotesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplenotesapp.data.entity.Note

@Database(
        entities = [Note::class], version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val dao: NoteDao
}