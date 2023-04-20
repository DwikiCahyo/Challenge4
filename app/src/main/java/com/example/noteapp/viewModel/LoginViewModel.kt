package com.example.noteapp.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
):ViewModel() {

    //save username
    fun saveUsernamePreferences(key: String,value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    //get username
    fun getUsernamePreferences(key: String):String?{
        return sharedPreferences.getString(key,"Username Kosong")
    }

    //save Password
    fun savePasswordPreferences(key: String,value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }

    //get Password
    fun getPasswordPreferences(key: String):String?{
        return sharedPreferences.getString(key,"Password Kosong")
    }


}