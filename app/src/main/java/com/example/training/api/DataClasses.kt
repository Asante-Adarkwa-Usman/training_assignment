package com.example.training.api

data class VideoDetails(
    val id: Int,
    val title: String,
    val thumbnailUrl: String,
    val duration: String,
    val uploadTime: String,
    val views: String,
    val author: String,
    val videoUrl: String,
    val description: String,
    val subscriber: String,
    val isLive: Boolean
)

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)
    class Loading<T> : Result<T>()
    class Error<T>(data: T? = null, message: String) : Result<T>(data, message)
}