package com.dataoracle.agrizen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dataoracle.agrizen.helper.constants
import com.dataoracle.agrizen.location.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar


class LocationLauncherActivity : AppCompatActivity() {
    private var mFusedLocationClient:FusedLocationProviderClient? = null
    private var mLastLocation: Location? = null
    val context = this
    val TAG = "LocationLauncher"
    var mAddressOutput: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this?.getPreferences(MODE_PRIVATE)
        val isUserLoggedIn = sharedPref.getBoolean(getString(R.string.is_user_logged_in), false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_location_launcher)
        findViewById<View>(R.id.chooseCurrentLocation).setOnClickListener(View.OnClickListener {
            if (foregroundPermissionApproved()) {
                getLocation()
            } else {
                requestForegroundPermissions()
            }
        })
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        mFusedLocationClient?.getLastLocation()
            ?.addOnSuccessListener(this,
                OnSuccessListener<Location?> { location ->
                    if (location == null) {
                        return@OnSuccessListener
                    }
                    mLastLocation = location

                    // Determine whether a Geocoder is available.
                    if (!Geocoder.isPresent()) {
                        showSnackbar("No geocode")
                        return@OnSuccessListener
                    }
                    mAddressOutput = LocationUtils.getCompleteAddressString(
                        this,
                        mLastLocation!!.latitude,
                        mLastLocation!!.longitude
                    )
                    setResultAndSendSuccess()
                })
            ?.addOnFailureListener(this,
                OnFailureListener { e -> Log.w(TAG, "getLastLocation:onFailure", e) })
    }

    private fun foregroundPermissionApproved(): Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()

        // If the user denied a previous request, but didn't check "Don't ask again", provide
        // additional rationale.
        if (provideRationale) {
            Snackbar.make(
                findViewById(R.id.activity_location_laucher),
                "Test",
                Snackbar.LENGTH_LONG
            )
                .setAction("OK") {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this@LocationLauncherActivity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        constants.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
                    )
                }
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this@LocationLauncherActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                constants.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult")

        when (requestCode) {
            constants.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->
                    Log.d(TAG, "User interaction was cancelled.")
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }

                else -> {
                    Snackbar.make(
                        findViewById(R.id.activity_location_laucher),
                        "Denied",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Settings") {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
            }
        }
    }

    private fun showSnackbar(text: String) {
        val container = findViewById<View>(R.id.activity_location_laucher)
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setResultAndSendSuccess() {
        val resultIntent = Intent()
        resultIntent.putExtra("user_location", mAddressOutput)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}