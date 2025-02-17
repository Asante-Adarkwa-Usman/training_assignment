package com.example.training.ui.composables.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.training.R
import com.example.training.api.Result
import com.example.training.api.VideoDetails
import com.example.training.ui.composables.widgets.VideoDetailCard
import com.example.training.vm.VideoViewModel

//Video Details Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    viewModel: VideoViewModel,
    videoId: Int?,
    onBack: () -> Unit
) {
    val videoList by viewModel.videoDetails.collectAsState()

    //Grabbing context
    val context = LocalContext.current

    // Remembering the scroll state
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.fetchVideoDetails() // Fetch video details when the screen is launched
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.video_details_text), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)

        ) {

            when (videoList) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                // Display the Video Detail Card
                is Result.Success -> {
                    val videos = (videoList as Result.Success<List<VideoDetails>>).data ?: emptyList()

                    if (videos.isNotEmpty()) {
                        val videoDetail = videos.find { video -> video.id == videoId }
                        if(videoDetail != null){
                            VideoDetailCard(video = videoDetail)
                        }
                    } else {
                        Text(text = stringResource(R.string.no_video_error), modifier = Modifier.padding(16.dp))
                    }
                }

                is Result.Error -> {
                    Text(
                        text = "Error: ${(videoList as Result.Error).message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(

                onClick = {
                    val videos = (videoList as Result.Success<List<VideoDetails>>).data ?: emptyList()
                    if (videos.isNotEmpty()) {
                        val videoDetail = videos.find { video -> video.id == videoId }
                    if (videoDetail != null) {
                        //Save video into database
                        viewModel.saveVideo(videoDetail, context)
                    }
                }},
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(stringResource(R.string.button_text))
            }
        }
    }
  }

