package com.example.test_task.MainModel

import com.example.test_task.Models.Retrofit.RetrofitService
import com.example.test_task.Models.Room.FavoriteDao
import javax.inject.Inject

class ModelsRepository {
    @Inject lateinit var retrofitService: RetrofitService
    @Inject lateinit var favoriteDao: FavoriteDao
}
var modelsRepository = ModelsRepository()