package com.kou.fisaa.presentation.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivityHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupNavontroller with navigationoption

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setupWithNavController(navController)

        /* val bomBg = binding.bottomAppBar.background as MaterialShapeDrawable
         bomBg.shapeAppearanceModel = bomBg.shapeAppearanceModel
             .toBuilder()
             .setTopRightCorner(CornerFamily.ROUNDED, 20f)
             .setTopLeftCorner(CornerFamily.ROUNDED, 20f).build()*/


    }


}