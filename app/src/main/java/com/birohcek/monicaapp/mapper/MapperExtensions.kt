package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.*
import com.birohcek.monicaapp.networking.models.*

object MapperExtensions {
    val countryMapper = CountryMapper()
    val addresMapper = AddressMapper()
    val dateMapper = SingleDateMapper()
    val howYouMetMapper = HowYouMetMapper(dateMapper)
    val carreerMapper = CarreerMapper()
    val contactFieldMapper = ContactEntryMapper()
    val relationshipContainerMapper = RelationshipContainerMapper()
}


private val ext = MapperExtensions

fun CountryDto.asModel() : CountryModel = ext.countryMapper.deserialize(this)
fun AddressDto.asModel() : AddressModel = ext.addresMapper.deserialize(this)
fun HowYouMetDto.asModel() : HowYouMetModel = ext.howYouMetMapper.deserialize(this)
fun CareerDto.asModel() : CarreerModel? = ext.carreerMapper.deserialize(this)
fun ContactFieldDto.asModel() = ext.contactFieldMapper.deserialize(this)
fun RelationshipTypeGroups?.asModel() = ext.relationshipContainerMapper.deserialize(this).orEmpty()