package com.example.test_task.Models.Retrofit

import com.example.test_task.Models.Offer
import com.example.test_task.Models.Vacancy
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("publicData/offers")
    suspend fun getOffers() : List<Offer>

    @GET("publicData/vacancies")
    suspend fun getVacancies() : List<Vacancy>

    @GET("publicData/vacancies")
    suspend fun getVacanciesBySearch(@Query("search") search: String) : List<Vacancy>

    @GET("publicData/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String) : Vacancy
}