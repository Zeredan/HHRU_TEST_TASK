package com.example.test_task.Models

import android.os.Parcelable

data class Button(
    val text: String?
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)

data class Experience(
    val previewText: String,
    val text: String
)

data class Salary(
    val short: String,
    val full: String
)



data class Offer(
    val id: String?,
    val title: String,
    val link: String,
    val button: Button?
)

data class Vacancy(
    val id : String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String,
    val questions : List<String>
)

data class DataChunk(
    val offers: List<Offer>,
    val vacancies: List<Vacancy>
)