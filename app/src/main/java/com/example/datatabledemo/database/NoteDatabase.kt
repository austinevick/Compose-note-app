package com.example.datatabledemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.datatabledemo.util.DateConverter

@TypeConverters(value = [DateConverter::class])
@Database(entities = [NoteEntity::class], version = 2)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao():NoteDao
}