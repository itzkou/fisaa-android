package com.kou.fisaa.presentation.host

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.ImageLoader
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import com.kou.fisaa.presentation.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        binding.bottomNavigationView.itemIconTintList = null
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
        val menuProfile = binding.bottomNavigationView.menu[4]
        viewModel.userPhoto.observe(this, { imageRes ->
            imageRes?.let { img ->
                lifecycleScope.launch {
                    val loader = ImageLoader(this@HostActivity)
                    val request = coil.request.ImageRequest.Builder(this@HostActivity)
                        .data(img)
                        .transformations(CircleCropTransformation())
                        .build()
                    val result = (loader.execute(request) as SuccessResult).drawable
                    val bitmap = (result as BitmapDrawable)
                    menuProfile.icon = bitmap

                }
            }

        })

    }
}
