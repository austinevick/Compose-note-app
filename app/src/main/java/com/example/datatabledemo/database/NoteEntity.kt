package com.example.datatabledemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.datatabledemo.util.Constants.Companion.DATABASE_TABLE
import java.util.Date

@Entity(tableName = DATABASE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val alarmDate: String? = null,
    val date: Date
)