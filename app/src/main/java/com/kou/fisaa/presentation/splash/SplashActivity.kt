package com.kou.fisaa.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.databinding.ActivitySplashBinding
import com.kou.fisaa.presentation.host.HostActivity
import com.kou.fisaa.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    private val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.userId.observe(this, { userId ->
            if (userId != null)
                startActivity(Intent(this, HostActivity::class.java))
            else
                activityScope.launch {
                    delay(2000)
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()

                }

        })


    }
}