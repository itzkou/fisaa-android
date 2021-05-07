package com.kou.fisaa.presentation.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.databinding.ActivitySignUpBinding
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.coordinatePwd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel: SignUpViewModel by viewModels()


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
                        viewmodel.signUpFirebase(user.email, user.password)
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
                        val user = resource.data?.user
                        if (user != null)
                            viewmodel.setFireToken()
                        // startActivity(Intent(this, HostActivity::class.java))
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



        binding.signUp.setOnClickListener {
            val email = binding.username.text.toString()
            val fname = binding.fname.text.toString()
            val lname = binding.lname.text.toString()
            val pass = binding.password.text.toString()
            val address = binding.address.text.toString()
            val cin = binding.cin.text.toString()
            val city = binding.city.text.toString()
            val country = binding.pays.text.toString()
            val birthdate = binding.birthdate.text.toString()
            val description = binding.desc.text.toString()
            val phone = binding.phone.text.toString()
            val zip = binding.zip.text.toString()
            viewmodel.signUp(
                email,
                pass,
                fname,
                lname,
                birthdate,
                cin,
                description,
                phone,
                address,
                city,
                country,
                zip
            )

        }
    }
}