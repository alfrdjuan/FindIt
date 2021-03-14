package com.codewithjun.findit.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.codewithjun.findit.network.BASE_URL
import com.codewithjun.findit.network.PlaceNetwork
import com.codewithjun.findit.network.model.AutocompleteResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivityRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val autocompleteResult = MutableLiveData<AutocompleteResult>()

    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }


    fun getAutocompleteResult(
        language: String,
        radius: Int,
        key: String,
        input: String,
        components: String,
        sessiontoken: String
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PlaceNetwork = retrofit.create(PlaceNetwork::class.java)

        service.getAutocompleteResult(input, sessiontoken, radius, language, components, key)
            .enqueue(object : Callback<AutocompleteResult> {
                override fun onResponse(
                    call: Call<AutocompleteResult>,
                    response: Response<AutocompleteResult>
                ) {
                    autocompleteResult.value = response.body()
                    Log.d("Search", "onResponse:${response.body()}")
                    Log.d("Search", "autocomplete value:${autocompleteResult.value}")
                }

                override fun onFailure(call: Call<AutocompleteResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }

            )
    }


}