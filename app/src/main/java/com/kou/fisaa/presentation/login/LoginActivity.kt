package com.kou.fisaa.presentation.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.databinding.ActivityLoginBinding
import com.kou.fisaa.presentation.host.HostActivity
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint  //huny namlou injection des dependances Activity/Fragment requires this tag in order for dagger to work
class LoginActivity : AppCompatActivity() {

    /******* SOCIAL AUTH *******/
    private val GOOGLE_SIGN = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.login.setOnClickListener {

            viewModel.loginResponse.observe(this, Observer { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        result?.let {
                            Log.d("mwah", result.status.toString())
                            Log.d("mwah", result.data!!.data.firstName.toString())
                        }
                    }

                    Resource.Status.ERROR -> {
                        result?.let {
                            Log.d("mwah", result.status.toString())
                        }
                    }

                    Resource.Status.LOADING -> {
                        result?.let {
                            Log.d("mwah", result.status.toString())
                        }
                    }
                }
            })

        }

        binding.imGoogle.setOnClickListener {

        }



    }


}