package com.codewithjun.findit.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData

class SharedPreferencesRepository(application: Application) {
    val isInitialRun = MutableLiveData<Boolean>()
    val deviceID = MutableLiveData<String>()

    fun getInitialRun(sharedPreferences: SharedPreferences) {
        isInitialRun.value = sharedPreferences.getBoolean("isInitialRun", true)
    }

    fun getDeviceID(sharedPreferences: SharedPreferences) {
        deviceID.value = sharedPreferences.getString("device_id", "")
    }

    fun setInitialRun(status: Boolean, sharedPreferences: SharedPreferences) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isInitialRun", status)
        editor.apply()
    }

    fun setDeviceID(deviceID: String, sharedPreferences: SharedPreferences) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("device_id", deviceID)
        editor.apply()
    }
}