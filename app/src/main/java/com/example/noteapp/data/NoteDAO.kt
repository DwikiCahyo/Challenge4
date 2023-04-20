package com.example.noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {

    @Insert
    fun insertNoteData(noteData: NoteData)

    @Query("SELECT*FROM NoteData ORDER BY titleNote ASC")
    fun getNoteData(): List<NoteData>

    @Query("SELECT *FROM NoteData ORDER BY titleNote DESC")
    fun getNoteDataDesc():List<NoteData>

    @Delete
    fun deleteAll(noteData: NoteData)

    @Update
    fun updateNoteData(noteData: NoteData)

}