package com.codewithjun.findit.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codewithjun.findit.network.model.AutocompleteResult
import com.codewithjun.findit.repository.SearchActivityRepository
import com.codewithjun.findit.repository.SharedPreferencesRepository

class SearchActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SearchActivityRepository(application)
    val autocompleteResult: LiveData<AutocompleteResult>
    val deviceID: LiveData<String>
    private val sharedPreferencesRepository = SharedPreferencesRepository(application)

    init {
        this.autocompleteResult = repository.autocompleteResult
        this.deviceID = sharedPreferencesRepository.deviceID
    }


    fun searchLocation(
        input: String,
        sessiontoken: String,
        radius: Int,
        language: String,
        components: String,
        key: String
    ) {
        repository.getAutocompleteResult(language, radius, key, input, components, sessiontoken)
    }

    fun getDeviceID(sharedPreferences: SharedPreferences) {
        sharedPreferencesRepository.getDeviceID(sharedPreferences)
    }


}