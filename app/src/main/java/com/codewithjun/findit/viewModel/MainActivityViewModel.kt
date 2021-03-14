package com.codewithjun.findit.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codewithjun.findit.network.model.GeocodeResult
import com.codewithjun.findit.network.model.PlaceDetailsResult
import com.codewithjun.findit.repository.LocationRepository
import com.codewithjun.findit.repository.MainActivityRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationRepository(application)
    private val mainActivityRepository = MainActivityRepository()
    val geocodePlaceResult: LiveData<GeocodeResult>
    val placeIDDetailsResult: LiveData<PlaceDetailsResult>

    init {
        this.geocodePlaceResult = mainActivityRepository.currentPlace
        this.placeIDDetailsResult = mainActivityRepository.currentPlaceDetailsResult
    }

    fun getLocationLiveData() = locationLiveData
    fun getPlaceID(latlng: String, language: String, region: String, key: String) {
        mainActivityRepository.getPlaceDataGeocode(latlng, language, region, key)
    }

    fun getPlaceDetails(
        place_id: String,
        language: String,
        region: String,
        fields: String,
        key: String
    ) {
        mainActivityRepository.getPlaceDetailsResult(place_id, language, region, fields, key)
    }
}