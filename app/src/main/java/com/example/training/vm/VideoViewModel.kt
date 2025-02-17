package com.example.training.vm

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.training.api.VideoDetails
import com.example.training.api.VideoRepo
import com.example.training.database.VideoDatabase
import com.example.training.database.VideoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.training.api.Result as ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class VideoViewModel @Inject constructor(
    db: VideoDatabase,
    private val videoRepo: VideoRepo
) : ViewModel() {

    //User Database
    private val videoDao = db.videoDao()

    // Using MutableStateFlow instead of LiveData
    private val _videoDetails = MutableStateFlow<ApiResult<List<VideoDetails>>>(ApiResult.Loading())
    val videoDetails: StateFlow<ApiResult<List<VideoDetails>>> get() = _videoDetails

    init {
        fetchVideoDetails()
    }

    // The fetchVideoDetails method
    fun fetchVideoDetails() {
        // Using Coroutines
        viewModelScope.launch(Dispatchers.IO) {
            // Collecting the Flow from the repository
            videoRepo.getVideoDetails().collectLatest { videoResult ->
                // Update the state flow with the new data
                _videoDetails.value = videoResult
            }
        }
    }

    //Save video to database
    @SuppressLint("SuspiciousIndentation")
    fun saveVideo(video: VideoDetails, context: Context) {
        val newDataEntity = VideoEntity(
            id = video.id,
            title = video.title,
            views = video.views,
            author = video.author,
            description = video.description)

        viewModelScope.launch(Dispatchers.IO) {
            // Check if video ID exists in the database
            val isExists = videoDao.isVideoExists(video.id)

            if (!isExists) {
                // Video doesn't exist, insert it
                videoDao.insertAll(newDataEntity)

                // Switch to the main thread to show the Toast
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Video saved successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Video already exists, show a Toast message
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Video already exists", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    //Get saved video from db
    val savedVideos: StateFlow<List<VideoEntity>> = videoDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}


