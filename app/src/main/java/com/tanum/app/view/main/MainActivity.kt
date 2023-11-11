package com.tanum.app.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tanum.app.R
import com.tanum.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_beranda,
                R.id.navigation_lahan_saya,
                R.id.navigation_artikel,
                R.id.navigation_profil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val openLahanSaya = intent.getBooleanExtra(EXTRA_LAHAN_SAYA, false)
        if (openLahanSaya) {
            navView.selectedItemId = R.id.navigation_lahan_saya
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_beranda) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }

    companion object {
        const val EXTRA_LAHAN_SAYA = "extra_lahan_saya"
    }
}