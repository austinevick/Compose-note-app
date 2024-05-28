package com.example.datatabledemo.di

import android.content.Context
import androidx.room.Room
import com.example.datatabledemo.database.MIGRATION_1_2
import com.example.datatabledemo.database.NoteDatabase
import com.example.datatabledemo.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: NoteDatabase) = database.noteDao()
}