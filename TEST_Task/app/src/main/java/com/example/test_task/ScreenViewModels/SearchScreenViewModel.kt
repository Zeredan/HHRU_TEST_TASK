package com.example.test_task.ScreenViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task.Models.Offer
import com.example.test_task.Models.Room.FavoriteEntity
import com.example.test_task.Models.Vacancy
import com.example.test_task.MainModel.modelsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    var offers = mutableStateListOf<Offer>()
    var vacancies = mutableStateListOf<Vacancy>()
    var favoriteIds by mutableStateOf(listOf<String>())

    var currentVacancy by mutableStateOf<Vacancy?>(null)

    init{
        updateOffers()
        updateVacancies()
        synchronizeFavorites()
    }


    fun updateOffers(){
        viewModelScope.launch {
            offers.clear()
            //offers.addAll(RetrofitDataModel.api.getOffers())
            offers.addAll(modelsRepository.retrofitService.getOffers())
        }
    }

    fun updateVacancies(){
        viewModelScope.launch {
            vacancies.clear()
            //vacancies.addAll(RetrofitDataModel.api.getVacancies())
            vacancies.addAll(modelsRepository.retrofitService.getVacancies())
        }
    }

    fun synchronizeFavorites(){
        viewModelScope.launch {
            modelsRepository.favoriteDao.getFavoriteIds().collect{
                favoriteIds = it.map {it1 ->  it1.vacancyId }
            }
        }
    }

    fun isFavorite(vacancy: Vacancy) : Boolean = favoriteIds.firstOrNull{ it == vacancy.id } != null

    fun toggleFavorite(vacancy: Vacancy){
        viewModelScope.launch {
            if (!isFavorite(vacancy)) {
                modelsRepository.favoriteDao.addFavoriteIds(FavoriteEntity(vacancy.id))
            } else {
                modelsRepository.favoriteDao.removeFavoriteId(FavoriteEntity(vacancy.id))
            }
        }
    }

}