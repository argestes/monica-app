package com.birohcek.monicaapp.screens.contacts.single

import com.birohcek.monicaapp.models.AddressModel
import com.birohcek.monicaapp.models.DateModel
import java.text.DateFormatSymbols

public fun DateModel.asDate(): String {
    return when (this) {
        is DateModel.Exact -> "$day.$month.$year"
        is DateModel.DayMonth -> "$day ${month.asMonthName()}"
        is DateModel.Age -> "$age years ago"
        else -> ""
    }
}

fun AddressModel.asText(): String {
    return listOfNotNull(
        street,
        city,
        province,
        postalCode,
        country?.name
    ).joinToString { it }
}

fun DateModel.asBirthDay(): String {
    return when (this) {
        is DateModel.Exact -> "$day.$month.$year"
        is DateModel.DayMonth -> "$day ${month.asMonthName()}"
        is DateModel.Age -> "$age years old"
        else -> ""
    }
}

fun Int.asMonthName() = DateFormatSymbols.getInstance().shortMonths[this - 1]
