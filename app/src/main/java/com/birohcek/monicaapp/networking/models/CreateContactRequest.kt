package com.birohcek.monicaapp.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateContactRequest(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String?,
    @SerialName("nickname") val nickName: String?,
    @SerialName("gender_id") val genderId: Int?,
    @SerialName("birthdate_day") val birthdateDay: Int? = null,
    @SerialName("birthdate_month") val birtdateMonth: Int? = null,
    @SerialName("birthdate_year") val birthdateYear: Int? = null,
    @SerialName("is_birthdate_known") val isBirthdateKnown: Boolean = false,
    @SerialName("birthdate_is_age_based") val isBirthdateAgeBased: Boolean = false,
    @SerialName("birthdate_age") val birthdateAge: Int? = null,
    @SerialName("is_partial") val isPartial: Boolean = false,
    @SerialName("is_deceased") val isDeceased: Boolean = false,
    @SerialName("deceased_date_day") val deceasedDateDay: Int? = null,
    @SerialName("deceased_date_month") val deceasedDateMonth: Int? = null,
    @SerialName("deceased_date_year") val deceasedDateYear: Int? = null,
    @SerialName("deceased_date_is_age_based") val deceasedDateIsAgeBased: Boolean = false,
    @SerialName("is_deceased_date_known") val isDeceasedDateKnown: Boolean = false,
)