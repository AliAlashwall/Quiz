package com.example.quizzard.data.data_source.local

import androidx.room.TypeConverter
import com.example.quizzard.domain.model.Scores
import com.example.quizzard.domain.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun convertToJsonString(user: User?): String {
        return Json.encodeToString(user)
    }

    @TypeConverter
    fun convertToUser(json: String): User? {
        return Json.decodeFromString(json)
    }

    @TypeConverter
    fun convertToJsonString(scores: List<Scores>): String {
        return Json.encodeToString(scores)
    }

    @TypeConverter
    fun convertToListOfScores(json: String): List<Scores> {
        return Json.decodeFromString(json)
    }
}