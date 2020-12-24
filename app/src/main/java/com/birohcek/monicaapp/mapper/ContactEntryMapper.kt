package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.ContactInfoEntryModel
import com.birohcek.monicaapp.models.ContactInfoType
import com.birohcek.monicaapp.networking.models.ContactFieldDto
import javax.inject.Inject

class ContactEntryMapper @Inject constructor() : Mapper<ContactFieldDto, ContactInfoEntryModel> {

    override fun deserialize(from: ContactFieldDto): ContactInfoEntryModel {

        return ContactInfoEntryModel(
            id = from.id,
            data = from.data ?: "",
            type = mapType(from.contactFieldType.type),
            typeId = from.contactFieldType.id,
            protocol = from.contactFieldType.protocol,
            contactFieldName = from.contactFieldType.name
        )
    }

    private fun mapType(type: String): ContactInfoType {
        return when (type) {
            "email" -> {
                ContactInfoType.MAIL
            }
            "phone" -> {
                ContactInfoType.PHONE
            }
            else -> {
                ContactInfoType.UNKNOWN
            }
        }
    }
}