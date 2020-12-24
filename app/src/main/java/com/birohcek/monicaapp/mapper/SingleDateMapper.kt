package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.DateModel
import com.birohcek.monicaapp.networking.models.SingleDateDto
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import kotlin.time.Duration


class SingleDateMapper : Mapper<SingleDateDto?, DateModel?> {
    override fun deserialize(from: SingleDateDto?): DateModel? {
        return handleDate(from)
    }

    private fun handleDate(singleDate: SingleDateDto?): DateModel? {
        val birthDate = singleDate ?: return null
        //1994-01-27T00:00:00Z

        val parse = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(birthDate.date ?: return null)
        val from = LocalDate.from(parse)
        val now: LocalDate = LocalDate.now()

        if (birthDate.isAgeBased ?: return null) {
            return DateModel.Age(Period.between(from, now).years)
        }

        if (birthDate.isYearUnknown ?: return null) {
            return DateModel.DayMonth(from.dayOfMonth, from.monthValue)
        }

        return DateModel.Exact(from.dayOfMonth, from.monthValue, from.year)
    }

}