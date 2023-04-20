package com.example.noteapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class NoteData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val titleNote:String,
    val contentNote:String
):Parcelable


