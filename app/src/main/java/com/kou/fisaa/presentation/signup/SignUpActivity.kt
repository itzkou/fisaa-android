package com.kou.fisaa.presentation.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
        coordinatePwd(binding.signUp, binding.password,binding.passInput)




        /*** Events ***/
        binding.signUp.setOnClickListener {
            signUpQuery = SignUpQuery(
                binding.username.text.toString(),
                binding.fname.text.toString(),
                binding.lname.text.toString(),
                binding.password.text.toString(),
             //TODO Solve Nullabity issues
                binding.address.text.toString(),
                binding.cin.text.toString().toInt(),
                binding.city.text.toString(),
                binding.pays.text.toString(),
                binding.birthdate.text.toString(),
                binding.desc.text.toString(),
                binding.phone.text.toString().toLong(),
                binding.zip.text.toString().toInt()
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