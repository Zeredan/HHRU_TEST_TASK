package com.example.test_task

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.test_task.DependencyInjection.Dagger.DaggerAppComponent
import com.example.test_task.MainModel.modelsRepository
import com.example.test_task.Screens.MainScreen
import com.example.test_task.ui.theme.TEST_TaskTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dagCreator = DaggerAppComponent
            .builder()
            .addUrl("http://192.168.1.3:8080")
            .addContext(applicationContext)
            .addDbName("favorite_db")
            .build()

        dagCreator.injectAllInRepository(modelsRepository)

        setContent {
            TEST_TaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
