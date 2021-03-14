package com.codewithjun.findit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codewithjun.findit.R
import com.codewithjun.findit.viewModel.MainActivityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onBackPressed() {
        exitProcess(0)
    }

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mMap: GoogleMap
    private lateinit var mMarker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (intent.hasExtra("place_id")) {
            val placeID = intent.getStringExtra("place_id")
            Log.d("placeid", "onMapReady: placeid$placeID")
            if (placeID != null) {
                getPlaceDetails(placeID)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.clear()

        //run when the map is ready (including when the user is back from another activity
        requestLocationUpdates()

        //set new marker
        mMap.setOnMapClickListener {
            mMap.clear()
            mMarker.remove()
            placeMarker(it)
            getGeocodePlaceData(it)
        }

        search_address_button.setOnClickListener {
            mMap.clear()
            mMarker.remove()
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        my_location_button.setOnClickListener {
            mMap.clear()
            mMarker.remove()
            requestLocationUpdates()
        }

    }

    private fun placeMarker(location: LatLng) {
        val markerOptions = MarkerOptions().position(location).draggable(true)
        mMarker = mMap.addMarker(markerOptions)
    }

    private fun getGeocodePlaceData(location: LatLng) {
        val latlngString = location.latitude.toString() + "," + location.longitude.toString()
        Log.d("MainActivity", "current latitude and longitude:$latlngString")

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getPlaceID(
            latlngString,
            "id",
            "id",
            getString(R.string.google_maps_key)
        )

        viewModel.geocodePlaceResult.observe(this, {
            if (it.status == "OK") {
                Log.d("MainActivity", "placeMarker place_id:${it.results[0].place_id}")
                getPlaceDetails(it.results[0].place_id)
            }
        })
    }

    //get the new latitude and longitude of current/designed location and move the camera
    private fun requestLocationUpdates() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLocationLiveData().observe(this, {
            val currentLocation = LatLng(it.lat, it.lng)
            Log.d("LAT", "requestLocationUpdates: Lat" + it.lat)
            Log.d("LNG", "requestLocationUpdates: Long" + it.lng)
            placeMarker(currentLocation)
            getGeocodePlaceData(currentLocation)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f))
        })
    }

    private fun getPlaceDetails(placeID: String) {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getPlaceDetails(
            placeID,
            "id",
            "id",
            "address_component,name,geometry,formatted_address",
            getString(R.string.google_maps_key)
        )

        viewModel.placeIDDetailsResult.observe(this, {
            place_name.text = it.result.name
            place_info.text = it.result.formatted_address

            val currentLocation =
                LatLng(it.result.geometry.location.lat, it.result.geometry.location.lng)
            if (intent.hasExtra("place_id")) {
                placeMarker(currentLocation)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f))
                intent.removeExtra("place_id")
            }
        })
    }
}