package com.kou.fisaa.presentation.host

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import com.kou.fisaa.presentation.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private val viewModel: HostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
         binding.bottomNavigationView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_create_ads)
        }


        binding.logout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }


    }


}