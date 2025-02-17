package com.example.training.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM videoEntity")
    fun getAll(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM videoEntity WHERE id IN (:videoIds)")
    fun loadAllByIds(videoIds: IntArray): List<VideoEntity>

    @Query("SELECT * FROM videoEntity WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): VideoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg videos: VideoEntity)

    // Method to check if video with the given ID already exists
    @Query("SELECT COUNT(*) FROM videoEntity WHERE id = :videoId")
    suspend fun isVideoExists(videoId: Int): Boolean

    @Delete
    fun delete(video: VideoEntity)
}