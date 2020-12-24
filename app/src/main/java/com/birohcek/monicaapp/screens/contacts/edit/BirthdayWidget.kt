package com.birohcek.monicaapp.screens.contacts.edit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.IntRange
import androidx.core.view.isGone
import com.birohcek.monicaapp.R
import com.birohcek.monicaapp.databinding.WigdetBirthdayBinding
import java.text.DateFormatSymbols
import java.time.LocalDate


class BirthdayWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val daysAdapter = ArrayAdapter(
        getContext(),
        R.layout.dropdown_menu_popup_item,
        (1..31).map { it.toString() }.toTypedArray()
    )

    private val monthsAdapter = ArrayAdapter(getContext(),
        R.layout.dropdown_menu_popup_item,
        (1..12).map { DateFormatSymbols().months[it - 1] }
    )

    private val currentYear = LocalDate.now().year
    private val yearsAdapter = ArrayAdapter(getContext(),
        R.layout.dropdown_menu_popup_item,
        (currentYear downTo currentYear - 12000).map { it.toString() }
    )

    private val ageAdapter = ArrayAdapter(getContext(),
        R.layout.dropdown_menu_popup_item,
        (0 .. 120).map { it.toString() }
    )

    fun dayMonth(day: Int, @IntRange(from = 1, to = 12) month: Int) {
        binding.agePicker.isGone = true
        ageText.isGone = true


        binding.yearPicker.isGone = true
        binding.dayPicker.isGone = false
        binding.monthPicker.isGone = false

        daysDropdown.setText(day.toString(), false)
        monthsDropdown.setText(DateFormatSymbols().months[month - 1], false)
    }

    fun dayMonthYear(day: Int, month: Int, year: Int) {
        binding.agePicker.isGone = true
        ageText.isGone = true

        binding.yearPicker.isGone = false
        binding.dayPicker.isGone = false
        binding.monthPicker.isGone = false

        daysDropdown.setText(day.toString(), false)
        monthsDropdown.setText(DateFormatSymbols().months[month - 1], false)
        yearsDropdown.setText(year.toString(), false)
    }

    fun showAge(age: Int) {
        binding.yearPicker.isGone = true
        binding.dayPicker.isGone = true
        binding.monthPicker.isGone = true

        binding.agePicker.isGone = false
        ageText.isGone = false

        ageDropdown.setText(age.toString(), false)
    }

    private val binding = WigdetBirthdayBinding.inflate(LayoutInflater.from(context), this, true)

    private val daysDropdown: AutoCompleteTextView = binding.actDay
    private val monthsDropdown: AutoCompleteTextView = binding.actMonth
    private val yearsDropdown: AutoCompleteTextView = binding.actYear
    private val ageDropdown: AutoCompleteTextView = binding.actAge
    private val ageText: TextView = binding.tvAgeLabel

    init {

        daysDropdown.setAdapter(daysAdapter)
        daysDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                onDaySelected(index + 1)
            }

        monthsDropdown.setAdapter(monthsAdapter)
        monthsDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                onMonthSelected(index)
            }

        yearsDropdown.setAdapter(yearsAdapter)
        yearsDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                onYearSelected(currentYear - index)
            }

        ageDropdown.setAdapter(ageAdapter)
        ageDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, index, _ ->
                onAgeSelected(index)
            }
    }

    var onMonthSelected = { month: Int -> }
    var onDaySelected = { day: Int -> }
    var onYearSelected = { year: Int -> }
    var onAgeSelected = { age: Int -> }
}