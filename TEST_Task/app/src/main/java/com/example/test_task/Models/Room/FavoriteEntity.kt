package com.example.test_task.Models.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey val vacancyId: String
)