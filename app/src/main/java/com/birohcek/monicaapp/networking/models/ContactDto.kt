package com.birohcek.monicaapp.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    @SerialName("id") val id: Long,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String?,
    @SerialName("information") val information: ContactInformationDto?,
    @SerialName("contactFields") val contactFields: List<ContactFieldDto>? = null,
    @SerialName("addresses") val addresses: List<AddressDto> = listOf(),
    @SerialName("hash_id") val hashId: String,
    @SerialName("nickname") val nickName: String? = null,
    @SerialName("complete_name") val completeName: String,
    @SerialName("initials") val initials: String,
    @SerialName("description") val description: String? = null,
    @SerialName("is_starred") val isStarred: Boolean,
    @SerialName("is_partial") val isPartial: Boolean,
    @SerialName("is_active") val isActive: Boolean,
    @SerialName("is_dead") val isDead: Boolean,
    @SerialName("is_me") val isMe: Boolean,
    @SerialName("last_called") val lastCalled: String? = null,
    @SerialName("last_activity_together") val lastActivityTogether: String? = null,
    @SerialName("stay_in_touch_frequency") val stayInTouchFrequency: Int? = null,
    @SerialName("stay_in_touch_trigger_date") val stayInTouchTriggerDate: String? = null,
)



@Serializable
class CountryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("iso") val iso: String
)

@Serializable
class AddressDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("street") val street: String?,
    @SerialName("city") val city: String?,
    @SerialName("province") val province: String?,
    @SerialName("postal_code") val postalCode: String?,
    @SerialName("country") val countryDto: CountryDto
)

@Serializable
class HowYouMetDto(
    @SerialName("general_information") val generalInfo: String? = "",
    @SerialName("first_met_date") val firstMetDate: SingleDateDto?,
    @SerialName("first_met_through_contact") val first_met_through: ContactDto?,
)

@Serializable
data class CareerDto(
    @SerialName("company") val company: String?,
    @SerialName("job") val job: String?,
)

@Serializable
class ContactInformationDto(
    @SerialName("avatar") val avatar: ContactAvatarDto,
    @SerialName("dates") val dates: ContactDatesDto? = null,
    @SerialName("relationships") val relationshipTypeGroups: RelationshipTypeGroups? = null,
    @SerialName("how_you_met") val howYouMet: HowYouMetDto? = null,
    @SerialName("career") val carreer: CareerDto? = null
)

@Serializable
class ContactAvatarDto(
    @SerialName("url") val url: String
)

@Serializable
class ContactDatesDto(
    @SerialName("birthdate") val birthDate: SingleDateDto,
    @SerialName("deceased_date") val deceasedDate: SingleDateDto,
)

@Serializable
class SingleDateDto(
    @SerialName("is_age_based") val isAgeBased: Boolean?,
    @SerialName("is_year_unknown") val isYearUnknown: Boolean?,
    @SerialName("date") val date: String?,
)

@Serializable
class UpdateBirthdateRequest(
    @SerialName("first_name") val firstName: String,
    @SerialName("birthdate_day") val day: Int?,
    @SerialName("birthdate_month") val month: Int?,
    @SerialName("birthdate_year") val year: Int?,
    @SerialName("birthdate_is_age_based") val isAgeBased: Boolean,
    @SerialName("is_birthdate_known") val isKnown: Boolean,
    @SerialName("is_deceased") val isDeceased: Boolean,
    @SerialName("is_deceased_date_known") val isDeceasedDateKnown: Boolean,
    @SerialName("birthdate_age") val age: Int
)

@Serializable
class RelationshipTypeGroups(
    @SerialName("love") val loveRelationship: RelationshipContacts,
    @SerialName("friend") val friendship: RelationshipContacts,
    @SerialName("family") val family: RelationshipContacts,
    @SerialName("work") val workRelations: RelationshipContacts,
)

@Serializable
class RelationshipContacts(
    @SerialName("total") val count: Int,
    @SerialName("contacts") val entries: List<RelationshipEntry>? = null
)

@Serializable
class RelationshipEntry(
    @SerialName("relationship") val typeDto: RelationshipTypeDto,
    @SerialName("contact") val contactDto: ContactDto
)

@Serializable
class RelationshipTypeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

@Serializable
class ContactFieldsResponse(val data: List<ContactFieldDto>)

@Serializable
class ContactFieldDto(
    @SerialName("id") val id: Int,
    @SerialName("content") val data: String,
    @SerialName("contact_field_type") val contactFieldType: ContactFieldType
)

@Serializable
class ContactFieldType(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("protocol") val protocol: String,
    @SerialName("type") val type: String,
)
