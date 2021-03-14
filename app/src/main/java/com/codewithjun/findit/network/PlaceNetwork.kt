package com.codewithjun.findit.network

import com.codewithjun.findit.network.model.AutocompleteResult
import com.codewithjun.findit.network.model.GeocodeResult
import com.codewithjun.findit.network.model.PlaceDetailsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://maps.googleapis.com/maps/"

interface PlaceNetwork {

    @GET("api/geocode/json?")
    fun getGeocodeResult(
        @Query("latlng") latlng: String?,
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("key") key: String?,
    ): Call<GeocodeResult>

    @GET("api/place/autocomplete/json?")
    fun getAutocompleteResult(
        @Query("input") input: String?,
        @Query("sessiontoken") sessionToken: String?,
        @Query("radius") radius: Int?,
        @Query("language") language: String?,
        @Query("components") components: String?,
        @Query("key") key: String?
    ): Call<AutocompleteResult>

    @GET("api/place/details/json?")
    fun getPlaceDetailsResult(
        @Query("place_id") placeID: String?,
        @Query("language") language: String?,
        @Query("region") region: String?,
        @Query("fields") fields: String?,
        @Query("key") key: String?
    ): Call<PlaceDetailsResult>
}