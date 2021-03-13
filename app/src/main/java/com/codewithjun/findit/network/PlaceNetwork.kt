package com.codewithjun.findit.network

import com.codewithjun.findit.network.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://maps.googleapis.com/maps"

interface PlaceNetwork {

    @GET("/api/place/details/json")
    fun getAutoCompleteResult(
        @Query("latlng") latlng: String?,
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("key") key: String?,
        @Query("input") input: String?
    ): Call<List<Result>>

    @GET("/maps/api/place/autocomplete/json")
    fun getGeocodeResult(
        @Query("components") components: String?,
        @Query("input") input: String?,
        @Query("radius") radius: Int?,
        @Query("language") language: String?,
        @Query("sessiontoken") sessionToken: String?,
        @Query("key") key: String?
    ): Call<List<Result>>

    @GET("maps/api/place/details/json")
    fun getPlaceDetails(
        @Query("place_id") placeID: String?,
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("fields") fields: Int?,
        @Query("key") key: String?
    ): Call<List<Result>>
}