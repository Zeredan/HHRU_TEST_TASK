package com.example.test_task.DependencyInjection.Dagger

import android.content.Context
import androidx.room.Room
import com.example.test_task.Models.Room.FavoriteDao
import com.example.test_task.Models.Room.FavoriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RoomDaggerModule {
    @Provides
    fun provideFavoriteDao(applicationContext: Context, @Named("dbName") dbName: String) : FavoriteDao {
        return Room.databaseBuilder(applicationContext, FavoriteDatabase::class.java, dbName).build().favoriteDao()
    }
}