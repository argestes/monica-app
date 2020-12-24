package com.birohcek.monicaapp.networking.models

import kotlinx.serialization.Serializable

@Serializable
class ContactsResponse(val data: List<ContactDto>)