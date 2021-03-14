package com.codewithjun.findit.repository

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import com.codewithjun.findit.network.model.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationRepository(application: Application) : LiveData<Location>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location ->
            location.also {
                setLocationData(it)
            }
        }
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult

            for (location in locationResult.locations) {
                setLocationData(location)
            }

        }
    }

    fun setLocationData(location: android.location.Location) {
        value = Location(location.latitude, location.longitude)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}