package com.example.quizzard.domain.repository

import com.example.quizzard.domain.model.User

interface LocalQuizRepository {
    suspend fun getUserHistory(): User
    suspend fun insertUser(user: User)
}