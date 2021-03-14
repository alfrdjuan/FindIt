package com.codewithjun.findit.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.codewithjun.findit.network.BASE_URL
import com.codewithjun.findit.network.PlaceNetwork
import com.codewithjun.findit.network.model.GeocodeResult
import com.codewithjun.findit.network.model.PlaceDetailsResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityRepository {

    val currentPlace = MutableLiveData<GeocodeResult>()
    val currentPlaceDetailsResult = MutableLiveData<PlaceDetailsResult>()

    fun getPlaceDataGeocode(latlng: String, language: String, region: String, key: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PlaceNetwork = retrofit.create(PlaceNetwork::class.java)

        service.getGeocodeResult(latlng, language, region, key)
            .enqueue(object : Callback<GeocodeResult> {
                override fun onResponse(
                    call: Call<GeocodeResult>,
                    response: Response<GeocodeResult>
                ) {
                    currentPlace.value = response.body()
                    Log.d("TAG", "onResponse:${response.body()} ")
                }

                override fun onFailure(call: Call<GeocodeResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun getPlaceDetailsResult(
        place_id: String,
        language: String,
        region: String,
        fields: String,
        key: String
    ) {
        Log.d("getPlaceDetailsResult", "getPlaceDetailsResult place_id: $place_id")
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val service: PlaceNetwork = retrofit.create(PlaceNetwork::class.java)

        service.getPlaceDetailsResult(place_id, language, region, fields, key)
            .enqueue(object : Callback<PlaceDetailsResult> {
                override fun onResponse(
                    call: Call<PlaceDetailsResult>,
                    response: Response<PlaceDetailsResult>
                ) {
                    currentPlaceDetailsResult.value = response.body()
                    Log.d("getPlaceDetailsResult", "onResponse:${response.body()} ")
                }

                override fun onFailure(call: Call<PlaceDetailsResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}