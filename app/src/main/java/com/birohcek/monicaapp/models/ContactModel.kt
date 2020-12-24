package com.birohcek.monicaapp.models

data class ContactModel(
    val id: Long,
    val firstName: String,
    val lastName: String?,
    val fullName: String,
    val pictureUrl: String?,
    val petModels: List<PetModel> = listOf(),
    val birthDate: DateModel?,
    val relationshipsContainer: List<RelationshipModel> = listOf(),
    val contactInfos: List<ContactInfoEntryModel> = listOf(),
    val howYouMet: HowYouMetModel? = null,
    val addresses: List<AddressModel> = listOf(),
    val career: CarreerModel? = null
)