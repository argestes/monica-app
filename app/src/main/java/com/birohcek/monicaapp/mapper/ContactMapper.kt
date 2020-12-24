package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.networking.models.ContactDto
import javax.inject.Inject

class ContactMapper @Inject constructor(
    private val singleDateMapper: SingleDateMapper
) : Mapper<ContactDto, ContactModel> {

    private fun fullname(it: ContactDto) = it.firstName + " " + (it.lastName ?: "")

    override fun deserialize(from: ContactDto): ContactModel {

        return ContactModel(
            from.id,
            from.firstName,
            from.lastName ?: "",
            fullname(from),
            petModels = listOf(),
            birthDate = singleDateMapper.deserialize(from.information?.dates?.birthDate),
            pictureUrl = from.information?.avatar?.url,
            relationshipsContainer = from.information?.relationshipTypeGroups.asModel(),
            contactInfos = from.contactFields?.map { it.asModel() }.orEmpty(),
            addresses = from.addresses.map { it.asModel() },
            howYouMet = from.information?.howYouMet?.asModel(),
            career = from.information?.carreer?.asModel()
        )
    }
}