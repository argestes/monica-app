package com.birohcek.monicaapp.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PetsResponse(val data: List<PetDto>)

@Serializable
class PetDto(val id: Long,
             val name: String,
             @SerialName("pet_category") val petCategoryDto: PetCategoryDto,
)

@Serializable
class PetCategoryDto(
    @SerialName("id") val id: Int,
    @SerialName("object") val obj: String,
    @SerialName("name") val name: String,
    @SerialName("is_common") val isCommon: Boolean
)