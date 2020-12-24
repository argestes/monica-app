package com.birohcek.monicaapp.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CredentialsDto(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
class TokenResponseDto(
    @SerialName("access_token") val token: String
)