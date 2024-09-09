package com.example.test_task.Models

fun interface Getable{
    abstract fun getWord(number: Int) : String
}

enum class Words(getable: Getable) : Getable by getable{

    `Человек`(Getable{
        when{
            it in listOf(*(10..20).toList().toTypedArray()) -> "человек"
            it.toString().run{ this[this.length - 1].digitToInt() } in listOf(2, 3, 4) -> "человека"
            else -> "человек"
        }
    }),
    `Вакансия`(Getable {
        when{
            it in kotlin.collections.listOf(*(10..20).toList().toTypedArray()) -> "вакансий"
            it.toString().run{ this[this.length - 1].digitToInt() } in kotlin.collections.listOf(1) -> "вакансия"
            it.toString().run{ this[this.length - 1].digitToInt() } in kotlin.collections.listOf(2, 3, 4) -> "вакансии"
            it.toString().run{ this[this.length - 1].digitToInt() } in kotlin.collections.listOf(5, 6, 7, 8, 9) -> "вакансий"
            else -> "вакансий"
        }
    });

    operator fun get(number: Int) : String{
        return this.getWord(number)
    }
}