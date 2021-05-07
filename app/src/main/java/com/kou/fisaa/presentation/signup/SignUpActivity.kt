package com.kou.fisaa.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.data.entities.SignUpQuery
import com.kou.fisaa.databinding.ActivitySignUpBinding
import com.kou.fisaa.presentation.host.HostActivity
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.coordinatePwd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel: SignUpViewModel by viewModels()
    private lateinit var signUpQuery: SignUpQuery


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*** UI Validation ***/
        setupUi()



        /*** Observables ***/
        viewmodel.signupResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        val user = resource.data!!
                        viewmodel.setId(user._id)
                        Toast.makeText(this, user._id, Toast.LENGTH_SHORT).show()
                        viewmodel.signUpFirebase(signUpQuery.email, signUpQuery.password)
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
        viewmodel.fireSignUpResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        viewmodel.setFireToken()
                        startActivity(Intent(this, HostActivity::class.java))
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

    private fun setupUi() {
        coordinateBtnAndInputs(binding.signUp, binding.fname, binding.lname, binding.username)
        coordinatePwd(binding.signUp, binding.password, binding.passInput)

        signUpQuery = SignUpQuery(
            binding.username.text.toString(),
            binding.fname.text.toString(),
            binding.lname.text.toString(),
            binding.password.text.toString(),
            if (!binding.address.text.isNullOrBlank()) binding.address.text.toString() else null,
            if (!binding.cin.text.isNullOrBlank()) binding.cin.text.toString().toInt() else 0,
            if (!binding.city.text.isNullOrBlank()) binding.city.text.toString() else null,
            if (!binding.pays.text.isNullOrBlank()) binding.pays.text.toString() else null,
            if (!binding.birthdate.text.isNullOrBlank()) binding.birthdate.text.toString() else null,
            if (!binding.desc.text.isNullOrBlank()) binding.desc.text.toString() else null,
            if (!binding.phone.text.isNullOrBlank()) binding.phone.text.toString()
                .toInt() else null,
            if (!binding.zip.text.isNullOrBlank()) binding.zip.text.toString().toInt() else null
        )

        binding.signUp.setOnClickListener {
            viewmodel.signUp(signUpQuery)

        }
    }
}