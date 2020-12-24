package com.birohcek.monicaapp.models

interface DateModel {
    data class Exact(val day: Int, val month: Int, val year: Int) : DateModel
    data class DayMonth(val day: Int, val month: Int) : DateModel
    data class Age(val age: Int) : DateModel
}