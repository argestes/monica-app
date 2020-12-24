package com.birohcek.monicaapp.networking

import com.birohcek.monicaapp.networking.models.ContactsResponse
import com.birohcek.monicaapp.networking.models.GendersResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GenderApi {
    @Headers("Accept: application/json")
    @GET("/api/genders")
    public fun fetchGenders(
        @Header("Authorization") fullToken: String,
    ): Single<GendersResponse>

}
