package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.CountryModel
import com.birohcek.monicaapp.networking.models.CountryDto

class CountryMapper : Mapper<CountryDto, CountryModel> {
    override fun deserialize(from: CountryDto): CountryModel {
        return CountryModel(from.id, from.name, from.iso)
    }
}
