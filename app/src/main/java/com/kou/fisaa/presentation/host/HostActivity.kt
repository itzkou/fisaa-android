package com.kou.fisaa.presentation.host

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import com.kou.fisaa.presentation.login.LoginActivity
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
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_create_ads)

        }
        // viewModel.toggleNightMode()
        viewModel.darkThemeEnabled.observe(this, { nightModeActive ->

            val defaultMode = if (nightModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(defaultMode)


        })
        viewModel.userId.observe(this, { userId ->
            if (userId == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })


        /* val bomBg = binding.bottomAppBar.background as MaterialShapeDrawable
         bomBg.shapeAppearanceModel = bomBg.shapeAppearanceModel
             .toBuilder()
             .setTopRightCorner(CornerFamily.ROUNDED, 20f)
             .setTopLeftCorner(CornerFamily.ROUNDED, 20f).build()*/


    }


}