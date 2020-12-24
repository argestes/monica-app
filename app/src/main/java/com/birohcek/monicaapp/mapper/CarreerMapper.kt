package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.CarreerModel
import com.birohcek.monicaapp.networking.models.CareerDto

class CarreerMapper : Mapper<CareerDto, CarreerModel?> {
    override fun deserialize(from: CareerDto): CarreerModel? {
        if (from.company == null && from.job == null) {
            return null
        }

        return CarreerModel(from.job ?: "", from.company ?: "")
    }
}