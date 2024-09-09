package com.example.test_task.Models.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteEntity")
    fun getFavoriteIds() : Flow<List<FavoriteEntity>>

    @Insert
    suspend fun addFavoriteIds(vararg ids: FavoriteEntity)

    @Delete
    suspend fun removeFavoriteId(id: FavoriteEntity)
}