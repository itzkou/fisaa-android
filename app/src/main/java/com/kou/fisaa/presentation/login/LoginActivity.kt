package com.kou.fisaa.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kou.fisaa.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint  //huny namlou injection des dependances Activity/Fragment requires this tag in order for dagger to work
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loginResponse.observe(this, Observer {
            it?.let {
                Log.d("loginResponse", it)
            }
        })
    }
}