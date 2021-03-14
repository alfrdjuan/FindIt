package com.codewithjun.findit.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData

class PermissionActivityRepository(application: Application) {
    val permissionStatus = MutableLiveData<Boolean>()

    fun onPermissionResult(granted: Boolean) {
        permissionStatus.value = granted
    }
}