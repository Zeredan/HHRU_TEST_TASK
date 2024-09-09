package com.example.test_task.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.test_task.R

@Composable
fun EmptyFullScreen(img: Int){
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(img),
        contentDescription = ""
    )
}