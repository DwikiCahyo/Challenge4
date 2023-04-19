package com.example.noteapp

import androidx.datastore.core.DataStore
import javax.inject.Inject
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceManager @Inject constructor(private val dataStore:DataStore<Preferences>) {

    //save username
  suspend fun saveUsername(username:String){
      dataStore.edit{
          it[USERNAME] = username
      }
  }

    //get username
    fun getUsername(): Flow<String> {
        return dataStore.data.map {
            it[USERNAME] ?: ""
        }
    }

    //save password
    suspend fun savePassword(password:String){
        dataStore.edit{
            it[USERNAME] = password
        }
    }

    //get password
    fun getPassword(): Flow<String> {
        return dataStore.data.map {
            it[PASSWORD] ?: ""
        }
    }

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
    }

}