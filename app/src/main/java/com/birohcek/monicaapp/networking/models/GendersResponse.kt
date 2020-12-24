package com.birohcek.monicaapp.networking.models

import kotlinx.serialization.Serializable

@Serializable
class GendersResponse(val data: List<Gender>)

@Serializable
class Gender(val id: Int, val name: String)