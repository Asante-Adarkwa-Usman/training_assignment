package com.example.training.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "views") val views: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "description") val description: String?
)