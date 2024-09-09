package com.example.test_task.ScreenViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LogInScreenViewModel : ViewModel() {
    var email by mutableStateOf("")

    var isError by mutableStateOf(false)

    var passwordCode by mutableStateOf("")

    fun isEmailValid() : Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}