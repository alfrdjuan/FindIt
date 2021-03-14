package com.codewithjun.findit.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codewithjun.findit.R
import com.codewithjun.findit.viewModel.PermissionActivityViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_permission.*
import java.util.*

class PermissionActivity : AppCompatActivity(), PermissionListener {

    private lateinit var viewModel: PermissionActivityViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this).get(PermissionActivityViewModel::class.java)

        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION).withListener(this)
            .check()

        viewModel.getInitialRun(sharedPreferences)

        viewModel.isInitialRun.observe(this, {
            Log.d("isinitialrun", "onCreate:$it")
            if (!it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
            }
        })

        viewModel.setDeviceID(UUID.randomUUID().toString().replace("-", ""), sharedPreferences)
        viewModel.deviceID.observe(this, {
            Log.d("deviceID", "onCreate:$it ")
        })

        viewModel.permissionRequest.observe(this, {
            if (it) {
                continue_button.visibility = View.VISIBLE
                permission_button.visibility = View.GONE
            } else {
                permission_button.visibility = View.VISIBLE
                continue_button.visibility = View.GONE
            }
        })

        continue_button.setOnClickListener {
            viewModel.setInitialRun(false, sharedPreferences)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        permission_button.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION).withListener(this)
                .check()
        }
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        viewModel = ViewModelProvider(this).get(PermissionActivityViewModel::class.java)
        viewModel.setPermissionStatus(true)
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        viewModel = ViewModelProvider(this).get(PermissionActivityViewModel::class.java)
        viewModel.setPermissionStatus(false)
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1!!.continuePermissionRequest()
    }
}