package com.example.training.api

import retrofit2.http.GET

interface ApiService {
    @GET("$BASE_URL/poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/14a27bd0bcd0cd323b35ad79cf3b493dddf6216b/videos.json?_sm_au_=iMVsNwSTMFnnZH4QNWq8jLtWj0HLJ")
    suspend fun getVideoList():List<VideoDetails>

    companion object{
        const val BASE_URL = "https://gist.githubusercontent.com/"
    }
}