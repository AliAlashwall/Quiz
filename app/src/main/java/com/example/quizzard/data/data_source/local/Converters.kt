package com.example.quizzard.data.data_source.local

import androidx.room.TypeConverter
import com.example.quizzard.domain.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun convertToJsonString(user: User?): String {
        return Json.encodeToString(user)
    }

    @TypeConverter
    fun convertToObject(json: String): User? {
        return Json.decodeFromString(json)
    }
}