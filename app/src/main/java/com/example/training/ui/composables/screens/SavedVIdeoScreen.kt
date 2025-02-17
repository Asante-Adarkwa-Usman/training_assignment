package com.example.training.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.training.database.VideoEntity
import com.example.training.vm.VideoViewModel

//Saved Video Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedVideoScreen(
    onBack: () -> Unit,
    viewModel: VideoViewModel

    ) {
    val savedVideos by viewModel.savedVideos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Videos") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (savedVideos.isEmpty()) {
                Text(
                    text = "No saved videos",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(savedVideos) { video ->
                        SavedVideoItem(video = video)
                    }
                }
            }
        }
    }
}

// Individual Saved Video Item
@Composable
fun SavedVideoItem(video: VideoEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        video.title?.let { Text(text = it, fontWeight = FontWeight.Bold) }
        Text(text = "Author: ${video.author}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Views: ${video.views}", style = MaterialTheme.typography.bodySmall)
    }
}