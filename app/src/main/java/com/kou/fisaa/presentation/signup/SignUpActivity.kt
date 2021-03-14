package com.kou.fisaa.presentation.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kou.fisaa.R
import com.kou.fisaa.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}