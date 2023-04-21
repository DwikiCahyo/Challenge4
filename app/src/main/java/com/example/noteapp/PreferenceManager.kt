package com.example.noteapp

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import javax.inject.Inject
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager @Inject constructor(private val dataStore:DataStore<Preferences>) {


    //get login state
    fun getLoginState():Flow<Boolean>{
        return dataStore.data.map {
            it[LOGIN_STATE] ?: false
        }
    }


    //save login state
    suspend fun saveLoginState(state:Boolean){
        dataStore.edit {
            it[LOGIN_STATE] = state
        }
    }

    //get sort state
    fun getSortState():Flow<Boolean>{
        return dataStore.data.map {
            it[SORT_STATE] ?: false
        }
    }

    //save sort state
   suspend fun saveSortState(state:Boolean){
       dataStore.edit{
           it[SORT_STATE] = state
       }
   }


    //logout
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }


    companion object{
        private val USERNAME = stringPreferencesKey("username")
        private val PASSWORD = stringPreferencesKey("password")
        private val LOGIN_STATE = booleanPreferencesKey("login_state")
        private val SORT_STATE = booleanPreferencesKey("sort_state")
    }

}