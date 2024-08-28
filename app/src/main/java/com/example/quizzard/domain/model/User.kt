package com.example.quizzard.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "scores")
    @TypeConverters(Scores::class)
    val scores: List<Scores>
)

@Serializable
data class Scores(
    @ColumnInfo(name = "score")
    val score: Int,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "category")
    val category: String
)