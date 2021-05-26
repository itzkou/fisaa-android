package com.kou.fisaa.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kou.fisaa.data.entities.User
import com.kou.fisaa.databinding.ActivitySignUpBinding
import com.kou.fisaa.presentation.host.HostActivity
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.coordinatePwd
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewmodel: SignUpViewModel by viewModels()
    private var user: User? = null


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
                    resource?.data.let { fisaaUser ->
                        fisaaUser?.let {
                            user = fisaaUser
                            this.toast(fisaaUser._id)
                            viewmodel.setId(fisaaUser._id)
                            viewmodel.signUpFirebase(fisaaUser.email, fisaaUser.password)
                        }


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
                        val firebaseUser = resource.data?.user
                        firebaseUser?.let {
                            viewmodel.setFireToken(firebaseUser.uid)
                            user?.let { user ->
                                viewmodel.signUpFirestore(user = user)

                            }

                        }
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
        viewmodel.firestoreSignUpResponse.observe(this, { resource ->
            if (resource.status == Resource.Status.SUCCESS) {
                val fireStoreUser = resource.data
                fireStoreUser?.let { response ->
                    Log.d("firo", response.toString())
                    startActivity(Intent(this, HostActivity::class.java))

                }
            } else if (resource.status == Resource.Status.ERROR) {
                resource?.let {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
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
            user = User(
                _id = "N/A",
                email = email,
                firstName = fname,
                lastName = lname,
                password = pass,
                adress = if (address.isEmpty()) "N/A" else address,
                cin = if (cin.isEmpty()) 0 else cin.toInt(),
                city = if (city.isEmpty()) "N/A" else city,
                country = if (country.isEmpty()) "N/A" else country,
                dateOfBirth = if (birthdate.isEmpty()) "N/A" else birthdate,
                description = if (description.isEmpty()) "N/A" else description,
                phoneNumber = if (phone.isEmpty()) 0 else phone.toLong(),
                zipCode = if (zip.isEmpty()) 0 else zip.toInt()
            )

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