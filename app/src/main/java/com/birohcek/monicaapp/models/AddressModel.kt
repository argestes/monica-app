package com.birohcek.monicaapp.models

import com.birohcek.monicaapp.networking.models.CountryDto

data class AddressModel(
    val id: Int,
    val name: String?,
    val street: String?,
    val city: String?,
    val province: String?,
    val postalCode: String?,
    val country: CountryModel?
)