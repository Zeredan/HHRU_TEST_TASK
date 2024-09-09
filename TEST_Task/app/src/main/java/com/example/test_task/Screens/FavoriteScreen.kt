package com.example.test_task.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_task.Models.Words
import com.example.test_task.R
import com.example.test_task.ScreenViewModels.MainScreenViewModel
import com.example.test_task.ScreenViewModels.SearchScreenViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    vm: SearchScreenViewModel = viewModel<SearchScreenViewModel>(),
    anchoredDraggableState: AnchoredDraggableState<String>
)
{
    val navController = rememberNavController()
    navController.enableOnBackPressed(false)
    NavHost(
        navController = navController,
        startDestination = "Show"
    )
    {
        composable("Show"){
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(20.dp)
                    .fillMaxSize()
            )
            {
                Spacer(Modifier.height(40.dp))
                Text("Избранное", fontSize = 24.sp, color = colorResource(R.color.White))
                Spacer(Modifier.height(15.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                )
                {
                    item{
                        Spacer(Modifier.height(5.dp))
                        Text("${vm.favoriteIds.size} ${Words.Вакансия[vm.favoriteIds.size]}", color = Color.White)
                        Spacer(Modifier.height(5.dp))
                    }
                    items(vm.vacancies.filter { vm.isFavorite(it) }) {
                        VacancyCard(vacancy = it, vm.isFavorite(it), { vm.currentVacancy = it; navController.navigate("Vacancy") }, vm)
                    }
                }
            }
        }
        composable("Vacancy"){
            VacancyShowcase(navController, vm)
        }
    }
}