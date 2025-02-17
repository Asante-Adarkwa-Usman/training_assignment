package com.example.training.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.training.ui.composables.screens.HomeScreen
import com.example.training.ui.composables.screens.SavedVideoScreen
import com.example.training.ui.composables.screens.VideoDetailScreen
import com.example.training.vm.VideoViewModel

@Composable
fun NavConfig (viewModel: VideoViewModel) {
    //setting a navController
    val navController = rememberNavController()
    //Setting a Navigation Host
    NavHost(navController = navController, startDestination="Home"){
        //The navigation graph
        composable(route = "Home"){
            HomeScreen(
                viewModel = viewModel ,
               goToSavedVideoScreen =  { navController.navigate(route = "SavedVideos") },
                goToVideoDetailScreen = { videoId ->
                    navController.navigate("videoDetail/$videoId") // FIXED: Pass actual ID
                }
            )
        }
        composable("videoDetail/{videoId}",arguments = listOf(
            navArgument("videoId") {
                type = NavType.IntType
            }
        )
        ) {
            val videoId = it.arguments?.getInt("videoId")
                VideoDetailScreen(
                    onBack = { navController.navigate("Home") },
                    viewModel = viewModel,
                    videoId = videoId
                )
        }
        composable(route = "SavedVideos"){
            SavedVideoScreen(
                onBack = { navController.navigate(route = "Home") },
                viewModel = viewModel
            ) 
        }
    }
}