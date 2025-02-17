package com.example.training.di

import android.content.Context
import androidx.room.Room
import com.example.training.api.ApiService
import com.example.training.api.VideoRepo
import com.example.training.api.VideoRepoImpl
import com.example.training.database.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoAppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiService.BASE_URL)
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesVideoRepo(apiService: ApiService): VideoRepo = VideoRepoImpl(apiService)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): VideoDatabase = Room.databaseBuilder(
        context,
        VideoDatabase::class.java, "video-database"
    ).build()

}