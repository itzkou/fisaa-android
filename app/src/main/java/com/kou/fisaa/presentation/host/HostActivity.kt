package com.kou.fisaa.presentation.host

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import com.kou.fisaa.presentation.splash.SplashActivity
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private val viewModel: HostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        getUserImage()
        logout()


    }


    private fun setUpUi() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.chatRoomFragment)
                binding.bottomNavigationView.visibility = View.GONE
            else
                binding.bottomNavigationView.visibility = View.VISIBLE

        }
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_create_ads)
        }
    }

    private fun logout() {
        binding.logout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

    }

    private fun getUserImage() {
        viewModel.userPhoto.observe(this, { imageRes ->
            imageRes?.let { img ->
                val menuProfile = binding.bottomNavigationView.menu[4]
                this.toast(img)
            }

        })


    }
}
