package com.example.test_task.ScreenViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.test_task.R
import com.example.test_task.Screens.EmptyFullScreen
import com.example.test_task.Screens.SearchScreen
import com.example.test_task.Screens.FavoriteScreen

class MainScreenViewModel : ViewModel() {
    data class ScreenInfo @OptIn(ExperimentalFoundationApi::class) constructor(
        val name: String,
        val imgNotSelected: Int,
        val imgSelected: Int,
        val notificationCount: Int = 0,
        val callingComposable: @Composable (AnchoredDraggableState<String>) -> Unit
    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalFoundationApi::class)
    var screens = mutableStateListOf<ScreenInfo>(
        ScreenInfo(
            "Поиск",
            R.drawable.search_gray,
            R.drawable.search_blue
        )
        {
            SearchScreen(anchoredDraggableState = it)
        },
        ScreenInfo(
            "Избранное",
            R.drawable.empty_heart,
            R.drawable.heart_active,
            1
        )
        {
            FavoriteScreen(anchoredDraggableState = it)
        },
        ScreenInfo(
            "Отклики",
            R.drawable.email_inactive,
            R.drawable.email
        )
        {
            EmptyFullScreen(img = R.drawable.jetpack_compose)
        },
        ScreenInfo(
            "Сообщения",
            R.drawable.message,
            R.drawable.message
        )
        {
            EmptyFullScreen(img = R.drawable.kotlin)
        },
        ScreenInfo(
            "Профиль",
            R.drawable.user_inactive,
            R.drawable.user_blue
        )
        {
            EmptyFullScreen(img = R.drawable.android)
        }
    )
}