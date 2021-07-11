package com.kou.fisaa.presentation.host

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.ImageLoader
import coil.request.ImageRequest
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import com.kou.fisaa.presentation.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private val viewModel: HostViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        viewModel.getImage()
        getUserImage()
        logout()


    }


    private fun setUpUi() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
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
                coroutineScope.launch {
                    val loader = ImageLoader(this@HostActivity)
                    val request = ImageRequest.Builder(this@HostActivity)
                        .data(img)
                        .target {
                            menuProfile.icon = it
                        }
                        .build()

                    loader.execute(request)

                }
            }

        })


    }
}
