package com.kou.fisaa.presentation.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.databinding.ActivityLoginBinding
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint  //huny namlou injection des dependances Activity/Fragment requires this tag in order for dagger to work
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.login.setOnClickListener {
            viewModel.fetchLoginResponse(LoginQuery(binding.username.text.toString(), binding.password.text.toString()))

        }



        binding.imGoogle.setOnClickListener {

        }

        viewModel.loginResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        if (it.data != null && it.data.success)
                            Toast.makeText(this, "Authorized", Toast.LENGTH_SHORT).show()
                        else Toast.makeText(this, "Unauthorized", Toast.LENGTH_SHORT).show()


                    }
                }

                Resource.Status.ERROR -> {
                    resource?.let {
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }

                Resource.Status.LOADING -> {
                    resource?.let {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


    }


}