package com.example.roomdatabasepractice.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val reaAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user:User)
    {
        userDao.addUser(user)
    }
}