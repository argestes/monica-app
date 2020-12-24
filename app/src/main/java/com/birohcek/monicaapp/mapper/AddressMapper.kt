package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.AddressModel
import com.birohcek.monicaapp.networking.models.AddressDto
import javax.inject.Inject

class AddressMapper @Inject constructor() :
    Mapper<AddressDto, AddressModel> {
    override fun deserialize(from: AddressDto): AddressModel {
        return AddressModel(
            from.id,
            from.name,
            from.street,
            from.city,
            from.province,
            from.postalCode,
            from.countryDto.asModel()
        )
    }
}