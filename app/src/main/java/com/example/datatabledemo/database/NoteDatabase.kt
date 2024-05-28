package com.example.datatabledemo.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.datatabledemo.util.Constants
import com.example.datatabledemo.util.DateConverter

@TypeConverters(value = [DateConverter::class])
@Database(entities = [NoteEntity::class], exportSchema = true, version = 2)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao():NoteDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("Alter TABLE ${Constants.DATABASE_TABLE} ADD COLUMN body TEXT ")
    }
}