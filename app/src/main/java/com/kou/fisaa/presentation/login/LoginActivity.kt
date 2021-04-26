package com.kou.fisaa.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.kou.fisaa.data.entities.LoginQuery
import com.kou.fisaa.databinding.ActivityLoginBinding
import com.kou.fisaa.presentation.host.HostActivity
import com.kou.fisaa.presentation.signup.SignUpActivity
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    /******* SOCIAL AUTH *******/
    private val GOOGLE_SIGN = 1

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*** UI Validation ***/
        coordinateBtnAndInputs(binding.login, binding.username, binding.password)

        /*** Events ***/
        binding.login.setOnClickListener {
            viewModel.fetchLoginResponse(
                LoginQuery(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            )

        }
        binding.imGoogle.setOnClickListener {
            signInWithGoogle()
        }
        binding.imFacebook.setOnClickListener {
            signInWithFacebook()
        }
        binding.createAcc.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        /*** Observables ***/
        viewModel.loginResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { loginResponse ->
                        if (resource.data.success) {
                            viewModel.setId(loginResponse.data._id)
                            startActivity(Intent(this, HostActivity::class.java))
                        } else Toast.makeText(this, "Unauthorized", Toast.LENGTH_SHORT).show()

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
        viewModel.googleResponse.observe(this, { resource ->
            if (resource.status == Resource.Status.SUCCESS) {
                resource?.let {
                    val user = resource.data!!.user!!
                    val fullName = user.displayName!!
                    val firstName: String = fullName.split(" ").first()
                    val lastName: String = fullName.split(" ").last()
                    viewModel.fetchLoginResponse(
                        LoginQuery(
                            user.email!!,
                            social = true,
                            image = user.photoUrl!!.toString(),
                            firstName = firstName,
                            lastName = lastName
                        )
                    )


                }
            } else if (resource.status == Resource.Status.ERROR) {
                resource?.let {
                    Log.d("googli", it.message.toString())
                }
            }
        })
        viewModel.facebookResponse.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {
                        val user = resource.data!!.user!!
                        val fullName = user.displayName!!
                        val firstName: String = fullName.split(" ").first()
                        val lastName: String = fullName.split(" ").last()

                        viewModel.fetchLoginResponse(
                            LoginQuery(
                                user.email!!,
                                social = true,
                                image = user.photoUrl!!.toString(),
                                firstName = firstName,
                                lastName = lastName
                            )
                        )


                    }
                }
                Resource.Status.ERROR -> {
                    resource?.let {
                        Log.d("facebooki", it.message.toString())
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                viewModel.signInWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun signInWithGoogle() {

        val signInIntent: Intent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, GOOGLE_SIGN)

    }

    private fun signInWithFacebook() {
        binding.btnFb.performClick()
        binding.btnFb.setReadPermissions(listOf("email", "public_profile"))
        binding.btnFb.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                val token = AccessToken.getCurrentAccessToken()
                if (token != null) {
                    val request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken()
                    ) { me, _ ->
                        val email = me.get("email").toString()
                        val fn = me.get("first_name").toString()
                        val ln = me.get("last_name").toString()
                        val pic =
                            me.getJSONObject("picture").getJSONObject("data").get("url").toString()

                        viewModel.signInWithFacebook(token)
                    }

                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id,first_name,last_name,picture.type(large), email"
                    )
                    request.parameters = parameters
                    request.executeAsync()


                }

            }

            override fun onCancel() {
                Log.e("FBLOGIN_FAILD", "Cancel")
            }

            override fun onError(error: FacebookException) {
                Log.e("FBLOGIN_FAILD", "ERROR", error)
            }
        })


    }


}