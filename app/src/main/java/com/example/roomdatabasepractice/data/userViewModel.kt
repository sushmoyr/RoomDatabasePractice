package com.example.roomdatabasepractice.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class userViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData:LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.reaAllData
    }

    fun addUser(user: User)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}