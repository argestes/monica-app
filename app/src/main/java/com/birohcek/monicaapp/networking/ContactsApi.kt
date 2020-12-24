package com.birohcek.monicaapp.networking

import com.birohcek.monicaapp.networking.models.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ContactsApi {

    @GET("/api/contacts/")
    public suspend fun getAllContacts(): ContactsResponse


    @Headers("Accept: application/json")
    @GET("/api/contacts/{id}?with=contactfields")
    public fun getSingleContact(
        @Header("Authorization") fullToken: String,
        @Path("id") id: Long
    ): Single<SingleContactResponse>

    @Headers("Accept: application/json")

    @GET("/api/contacts/{id}?with=contactfields")
    suspend fun getSingleContactSuspending(@Path("id") id: Long): SingleContactResponse

    @Headers("Accept: application/json")
    @GET("/api/contacts/{id}/pets")
    suspend fun getPetsOfSuspending(@Path("id") id: Long): PetsResponse

    @Headers("Accept: application/json")
    @GET("/api/contacts/{id}/pets")
    public fun getPetsOf(@Path("id") id: Long
    ): Single<PetsResponse>


    @Headers("Accept: application/json")
    @PUT("/api/contacts/{id}")
    public fun updateBirthDate(
        @Header("Authorization") fullToken: String,
        @Path("id") id: Long,
        @Body request: UpdateBirthdateRequest
    ): Single<SingleContactResponse>

    @Headers("Accept: application/json")
    @POST("/api/contacts")
    fun createContact(
        @Header("Authorization") fullToken: String,
        @Body createContactRequest: CreateContactRequest
    ): Single<SingleContactResponse>


    @GET("/api/contacts/{id}/contactfields")
    fun getContactFields(
        @Header("Authorization") fullToken: String,
        @Path("id") id: Long
    ): Single<ContactFieldsResponse>
}