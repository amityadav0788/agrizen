package com.dataoracle.agrizen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dataoracle.agrizen.helper.constants

class MainActivity : AppCompatActivity() {
    val LOG_TAG = "com.dataoracle.agrizen.mainactivity"
    var isUserLoggedIn = false
    var isLocationSet = false
    var userLocation : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        isUserLoggedIn = sharedPref.getBoolean(getString(R.string.is_user_logged_in), false);
        userLocation = sharedPref.getString(getString(R.string.user_location), "")
        if(userLocation?.length!! > 0)
            isLocationSet = true

        if(!isUserLoggedIn) {
            launchLoginPage()
        } else if(!isLocationSet) {
            launchLocationLauncher()
        } else
            launchHomePage()
    }

    fun launchHomePage() {
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun launchLoginPage() {
        val message = "Launch Login"
        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(LOG_TAG, message)
        }
        startActivityForResult(intent, constants.LAUNCH_LOGIN_CODE);
    }

    fun launchLocationLauncher() {
        val message = "Launch location"
        val intent = Intent(this, LocationLauncherActivity::class.java).apply {
            putExtra(LOG_TAG, message)
        }
        startActivityForResult(intent, constants.LAUNCH_LOCATION_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == constants.LAUNCH_LOGIN_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(getString(R.string.is_user_logged_in), true)
                    commit()
                }
                if(isLocationSet)
                    launchHomePage()
                else
                    launchLocationLauncher()
            }
        } else if(requestCode == constants.LAUNCH_LOCATION_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    if (data != null) {
                        putString(getString(R.string.user_location), data.getStringExtra("user_location"))
                    }
                    commit()
                }
                launchHomePage()
            }
        }
    }
}