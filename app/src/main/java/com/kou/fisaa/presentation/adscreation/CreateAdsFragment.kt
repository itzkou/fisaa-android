package com.kou.fisaa.presentation.adscreation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.AdsQuery
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.data.entities.Parcel
import com.kou.fisaa.databinding.FragmentCreateAdsBinding
import com.kou.fisaa.presentation.camera.CameraActivity
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.CustomAdapter
import com.kou.fisaa.utils.Resource
import com.kou.fisaa.utils.coordinateBtnAndInputs
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class CreateAdsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateAdsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateAdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d("myURI", data.toString())
            }
        }
    private lateinit var userId: String
    private lateinit var imageUri: Uri


    private var m1 = Material("Clothes", R.drawable.box_blue)
    private var m2 = Material("Food", R.drawable.box_blue)
    private var m3 = Material("Bois", R.drawable.box_blue)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAdsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUi()
        viewModel.userId.observe(viewLifecycleOwner, { id ->
            id?.let {
                userId = id
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adCreationResponse.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {

                        Toast.makeText(
                            requireActivity(),
                            " Ad created by ${it.data?.createdBy}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                Resource.Status.ERROR -> {
                    resource?.let {

                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                Resource.Status.LOADING -> {
                    resource?.let {

                        Toast.makeText(requireActivity(), "Loading", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
        viewModel.parcelCreationResponse.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource?.let {

                        Toast.makeText(
                            requireActivity(),
                            " Parcel created  ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Resource.Status.ERROR -> {
                    resource?.let {
                        Log.d("pur", resource.message.toString())
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                Resource.Status.LOADING -> {
                    resource?.let {

                        Toast.makeText(requireActivity(), "Loading", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupUi() {
        binding.publish.isEnabled = false
        binding.rdParcel.setOnClickListener(this)
        binding.rdTransport.setOnClickListener(this)
        binding.rdTravel.setOnClickListener(this)
        binding.spinnerParcelType.adapter =
            CustomAdapter(requireActivity(), arrayListOf(m1, m2, m3))
        binding.edDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDate)
        }
        binding.uploadImage.setOnClickListener {
            openCamera()
        }

    }

    fun openCamera() {
        val intent = Intent(activity, CameraActivity::class.java)
        resultLauncher.launch(intent)
    }

    override fun onClick(view: View?) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                binding.rdParcel.id -> {

                    if (checked) {
                        binding.publish.isEnabled = false
                        binding.consToHide.visibility = View.VISIBLE
                        coordinateBtnAndInputs(
                            binding.publish,
                            binding.departure,
                            binding.destination
                        )
                        binding.publish.setOnClickListener {
                            val chosenFile = File("kou")
                            val requestFile =
                                RequestBody.create(MediaType.parse("image/*"), chosenFile)
                            val image =
                                MultipartBody.Part.createFormData("image", "wuh", requestFile)
                            val parcel = Parcel(
                                14,
                                "wow 14 dinars mala sfa9",
                                "25",
                                "keys",
                                "",
                                22
                            )
                            val body = MultipartBody.Builder()
                                .addFormDataPart("dimension", parcel.dimension)
                                .addFormDataPart("bonus", parcel.bonus.toString())
                                .addFormDataPart("parcelType", parcel.parcelType)
                                .addFormDataPart("weight", parcel.weight.toString())
                                .build()
                            Log.d("mehParcel", body.part(0).toString())
                            //viewModel.postParcel(body = body)
                        }
                    }

                }

                binding.rdTravel.id -> {
                    if (checked) {
                        binding.publish.isEnabled = false
                        binding.consToHide.visibility = View.GONE
                        coordinateBtnAndInputs(
                            binding.publish,
                            binding.departure,
                            binding.destination
                        )

                        binding.publish.setOnClickListener {
                            val date = binding.edDate.text.toString()
                            val dep = binding.departure.text.toString()
                            val dest = binding.destination.text.toString()
                            viewModel.postAd(AdsQuery("", userId, dep, date, dest, "travel", null))
                        }

                    }
                }

                binding.rdTransport.id -> {
                    if (checked) {
                        binding.publish.isEnabled = false
                        binding.consToHide.visibility = View.VISIBLE

                    }
                }
            }
        }
    }
}








