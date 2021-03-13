package com.codewithjun.findit.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.codewithjun.findit.network.BASE_URL
import com.codewithjun.findit.network.PlaceNetwork
import com.codewithjun.findit.network.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivityRepository(val application: Application) {

    val showProgress = MutableLiveData<Boolean>()
    val resultList = MutableLiveData<List<Result>>()


    fun changeState() {
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }


    fun searchPlace(latlng: String, language: String, region: String, key: String, input: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PlaceNetwork = retrofit.create(PlaceNetwork::class.java)


        service.getAutoCompleteResult(latlng, language, region, key, input)
            .enqueue(object : Callback<List<Result>> {
                override fun onResponse(
                    call: Call<List<Result>>,
                    response: Response<List<Result>>
                ) {
                    resultList.value = response.body()
                }

                override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }

            )
    }
}