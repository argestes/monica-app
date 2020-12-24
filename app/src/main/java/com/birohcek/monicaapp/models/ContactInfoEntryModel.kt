package com.birohcek.monicaapp.models


class ContactInfoEntryModel(
    val id: Int,
    val data: String,
    val type: ContactInfoType,
    val typeId: Int,
    val protocol: String,
    val contactFieldName: String
)