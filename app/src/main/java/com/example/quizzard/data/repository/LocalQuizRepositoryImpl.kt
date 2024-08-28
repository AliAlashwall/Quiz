package com.example.quizzard.data.repository

import com.example.quizzard.data.data_source.local.UserDao
import com.example.quizzard.domain.model.User
import com.example.quizzard.domain.repository.LocalQuizRepository

class LocalQuizRepositoryImpl(private val userDao: UserDao): LocalQuizRepository {
    override suspend fun getUserHistory(): User = userDao.getUser()
    override suspend fun insertUser(user: User)  = userDao.insertUser(user)
}