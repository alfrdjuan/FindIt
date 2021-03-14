package com.codewithjun.findit.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codewithjun.findit.repository.PermissionActivityRepository
import com.codewithjun.findit.repository.SharedPreferencesRepository

class PermissionActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesRepository = SharedPreferencesRepository(application)
    private val repository = PermissionActivityRepository(application)

    val permissionRequest: LiveData<Boolean>
    val isInitialRun: LiveData<Boolean>
    val deviceID: LiveData<String>

    init {
        this.permissionRequest = repository.permissionStatus
        this.isInitialRun = sharedPreferencesRepository.isInitialRun
        this.deviceID = sharedPreferencesRepository.deviceID
    }

    fun setPermissionStatus(status: Boolean) {
        repository.onPermissionResult(status)
    }

    fun getInitialRun(sharedPreferences: SharedPreferences) {
        sharedPreferencesRepository.getInitialRun(sharedPreferences)
    }

    fun setInitialRun(status: Boolean, sharedPreferences: SharedPreferences) {
        sharedPreferencesRepository.setInitialRun(false, sharedPreferences)
    }

    fun setDeviceID(deviceID: String, sharedPreferences: SharedPreferences) {
        sharedPreferencesRepository.setDeviceID(deviceID, sharedPreferences)
    }

}