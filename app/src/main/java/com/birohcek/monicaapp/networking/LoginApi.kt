package com.birohcek.monicaapp.networking

import com.birohcek.monicaapp.networking.models.CredentialsDto
import com.birohcek.monicaapp.networking.models.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

//    https://app.monicahq.com/oauth/login

    @POST("oauth/login")
    @FormUrlEncoded
    suspend fun login( @Field("email") email: String,
                       @Field("password") password: String): TokenResponseDto
}