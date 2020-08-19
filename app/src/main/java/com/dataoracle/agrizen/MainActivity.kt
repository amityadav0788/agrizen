package com.dataoracle.agrizen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    val LOG_TAG = "com.dataoracle.agrizen.mainactivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPref.getBoolean(getString(R.string.is_user_logged_in), false);
        if(isUserLoggedIn) {
            launchHomePage()
        } else {
            launchLoginPage()
        }
    }

    fun launchHomePage() {
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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
        startActivity(intent);
    }
}