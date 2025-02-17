package com.example.training.ui.composables.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.training.R
import com.example.training.api.Result
import com.example.training.api.VideoDetails
import com.example.training.ui.composables.widgets.VideoCard
import com.example.training.vm.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VideoViewModel,
    goToSavedVideoScreen: () -> Unit,
    goToVideoDetailScreen: (id: Int) -> Unit
) {
    val videoListState by viewModel.videoDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchVideoDetails() // Fetch video details when the screen is launched
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Videos") },
                actions = {
                    Button(onClick = { goToSavedVideoScreen() }) {
                        Text(stringResource(R.string.saved_video_text))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (videoListState) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Result.Success -> {
                    val videos = (videoListState as Result.Success<List<VideoDetails>>).data ?: emptyList()
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(videos) { video ->
                            VideoCard(
                                video = video,
                                onClick = { goToVideoDetailScreen(video.id) }
                            )
                        }
                    }
                }
                is Result.Error -> {
                    Text(
                        text = "Error: ${(videoListState as Result.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
