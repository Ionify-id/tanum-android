package com.tanum.app.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tanum.app.databinding.ActivitySplashScreenBinding
import com.tanum.app.view.login.LoginActivity
import com.tanum.app.view.main.MainActivity
import com.tanum.app.viewmodels.SplashScreenViewModel
import com.tanum.app.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var factory: ViewModelFactory
    private val splashScreenViewModel: SplashScreenViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(this)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        var tokenIsEmpty = true

        lifecycleScope.launch {
            splashScreenViewModel.token.collect { token ->
                if (token.isNotEmpty()) tokenIsEmpty = false
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intentClass = if (tokenIsEmpty) LoginActivity::class.java else MainActivity::class.java
            val intent = Intent(this@SplashScreenActivity, intentClass)
            startActivity(intent)
            finish()
        }, 3000)

    }
}