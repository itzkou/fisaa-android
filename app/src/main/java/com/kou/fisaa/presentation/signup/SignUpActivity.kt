package com.kou.fisaa.presentation.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.data.entities.SignUpQuery
import com.kou.fisaa.databinding.ActivitySignUpBinding
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.coordinatePwd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel: SignUpViewModel by viewModels()
    private var signUpQuery: SignUpQuery? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*** UI Validation ***/
        coordinateBtnAndInputs(binding.signUp, binding.fname, binding.lname, binding.username)
        coordinatePwd(binding.signUp, binding.password, binding.passInput)



        /*** Events ***/
        binding.signUp.setOnClickListener {
            signUpQuery = SignUpQuery(
                binding.username.text.toString(),
                binding.fname.text.toString(),
                binding.lname.text.toString(),
                binding.password.text.toString(),
                //TODO Solve Nullabity issues
                if (!binding.address.text.isNullOrBlank()) binding.address.text.toString() else "",
                if (!binding.cin.text.isNullOrBlank()) binding.cin.text.toString().toInt() else 0,
                if (!binding.city.text.isNullOrBlank()) binding.city.text.toString() else "",
                if (!binding.pays.text.isNullOrBlank()) binding.pays.text.toString() else "",
                if (!binding.birthdate.text.isNullOrBlank()) binding.birthdate.text.toString() else "",
                if (!binding.desc.text.isNullOrBlank()) binding.desc.text.toString() else "",
                if (!binding.phone.text.isNullOrBlank()) binding.phone.text.toString()
                    .toLong() else 0L,
                if (!binding.zip.text.isNullOrBlank()) binding.zip.text.toString().toInt() else 0
            )

            viewmodel.signUp(signUpQuery!!)
        }

        /*** Observables ***/
        viewmodel.signupResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        Toast.makeText(this, resource.data!!._id, Toast.LENGTH_SHORT).show()
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