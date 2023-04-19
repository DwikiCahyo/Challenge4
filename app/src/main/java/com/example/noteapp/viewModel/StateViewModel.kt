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
class StateViewModel  @Inject constructor(private val prefManager:PreferenceManager):ViewModel() {

    //get login state
    fun getLoginState(): LiveData<Boolean> {
        return prefManager.getLoginState().asLiveData()
    }

    //save login state
    fun saveLoginState(state:Boolean){
        viewModelScope.launch {
            prefManager.saveLoginState(state)
        }
    }

    //logout
    fun logout(){
        viewModelScope.launch {
            prefManager.logout()
        }
    }


}