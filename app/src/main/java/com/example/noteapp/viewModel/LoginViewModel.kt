package com.example.noteapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val prefManager:PreferenceManager):ViewModel() {

    //get username
    fun getUsername():LiveData<String>{
        return prefManager.getUsername().asLiveData()
    }

    //save username
    fun saveUsername(username:String){
        viewModelScope.launch {
            prefManager.saveUsername(username)
        }
    }

    //get password
    fun getPassword():LiveData<String>{
        return prefManager.getPassword().asLiveData()
    }

    //save password
    fun savePassword(password:String){
        viewModelScope.launch {
            prefManager.savePassword(password)
        }
    }

}