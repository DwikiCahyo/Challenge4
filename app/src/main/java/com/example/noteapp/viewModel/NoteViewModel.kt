package com.example.noteapp.viewModel

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.NoteData
import com.example.noteapp.data.NoteDatabase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteViewModel(application: Application):AndroidViewModel(application) {

    var listNote:MutableLiveData<List<NoteData>> = MutableLiveData()

    init {
        getListNote()
    }

    fun getListNoteObserver():MutableLiveData<List<NoteData>>{
        return listNote
    }

    fun getListNote(){
        viewModelScope.launch(Dispatchers.IO){
            val noteDao = NoteDatabase.getInstance(getApplication())?.noteDao()
            val listNoteData = noteDao!!.getNoteData()
            listNote.postValue(listNoteData)
        }
    }

    //observer for descending data
    fun getListNoteDescObserver():MutableLiveData<List<NoteData>>{
        return listNote
    }


    //data desecnding
    fun getListNoteDesc(){
        viewModelScope.launch(Dispatchers.IO){
            val noteDao = NoteDatabase.getInstance(getApplication())?.noteDao()
            val listNoteData = noteDao!!.getNoteDataDesc()
            listNote.postValue(listNoteData)
        }

    }

    fun updateNote(id:Int,title:String, content:String){
        viewModelScope.launch(Dispatchers.IO) {
            val userDao = NoteDatabase.getInstance(getApplication())!!.noteDao()
            userDao.updateNoteData(NoteData(id,title,content))
        }
    }


}