package com.example.training.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepoImpl@Inject constructor(private val apiService: ApiService): VideoRepo {
    override suspend fun getVideoDetails(): Flow<Result<List<VideoDetails>>> = flow {
        emit(Result.Loading())
        val masterList = try {
            apiService.getVideoList()
        } catch (e:Exception){
            e.printStackTrace()
            emit(Result.Error(message = "Bad happened"))
            return@flow
        }
        emit(Result.Success(masterList))
    }
}