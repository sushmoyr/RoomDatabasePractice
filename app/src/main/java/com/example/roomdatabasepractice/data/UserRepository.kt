package com.example.roomdatabasepractice.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user:User)
    {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User)
    {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User)
    {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUser()
    {
        userDao.deleteAllUser()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<User>> {
        return userDao.searchDatabase(searchQuery)
    }

    fun sortData() : LiveData<List<User>>{
        return userDao.sortData()
    }
}