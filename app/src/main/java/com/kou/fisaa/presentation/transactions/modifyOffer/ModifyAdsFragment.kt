package com.kou.fisaa.presentation.transactions.modifyOffer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.databinding.FragmentModifyAdsBinding
import com.kou.fisaa.presentation.camera.CameraActivity
import com.kou.fisaa.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ModifyAdsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentModifyAdsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: ModifyAdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private val modifyAdsArgs: ModifyAdsFragmentArgs by navArgs()
    private var imageUri: Uri? = null
    private val parcelTypes =
        arrayListOf("clothing", "electronic", "books", "documents", "food", "other...")
    private val parcelWeights = arrayListOf("1K-2K", "3K-8K", "9K-20K", "20K+")
    private var dimension = "medium"
    private var parcelType = "other..."
    private var parcelWeight = "1K-2K"
    private lateinit var builderLoading: BuilderLoading
    private val getUriFromCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                imageUri = Uri.parse(result.data?.getStringExtra("cameraX"))
                binding.imageToUpload.setImageURI(imageUri)

            } else Log.d("CreateAdsFragment", "Uri from camera is null")
        }


    @Inject
    lateinit var materialAdapter: MaterialAdapter

    @Inject
    lateinit var weightAdapter: ArrayAdapter<String>

    @Inject
    lateinit var places: List<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentModifyAdsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUi()
        Log.i("modifyyAdsfragment", modifyAdsArgs.toString())
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.imageUrl.observe(viewLifecycleOwner, { url ->
            requireActivity().toast(url)

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        if (view is RadioButton) {

            when (view.getId()) {
                /** Dimension ***/
                binding.rdLarge.id -> dimension = "very large"

                binding.rdBig.id -> dimension = "large"

                binding.rdMedium.id -> dimension = "medium"

                binding.rdSmall.id -> dimension = "small"
            }
        }
    }

    private fun setupUi() {
        /*** EditTexts **/
        binding.edDate.setText(modifyAdsArgs.depDate.substring(0, 10))
        binding.departure.setText(modifyAdsArgs.departure)
        binding.destination.setText(modifyAdsArgs.destination)
        binding.imageToUpload.load(modifyAdsArgs.photo)
        binding.description.setText(modifyAdsArgs.description)
        binding.txBonus.setText(modifyAdsArgs.bonus.toString())
        binding.edDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDate)
        }
        /*** Loader ***/
        builderLoading = BuilderLoading(requireContext())
        /*** radio btns ***/
        binding.rdLarge.setOnClickListener(this)
        binding.rdBig.setOnClickListener(this)
        binding.rdMedium.setOnClickListener(this)
        binding.rdSmall.setOnClickListener(this)
        when (modifyAdsArgs.dimension) {
            "small" -> binding.rdSmall.isChecked = true
            "medium" -> binding.rdMedium.isChecked = true
            "large" -> binding.rdBig.isChecked = true
            "very large" -> binding.rdLarge.isChecked = true
        }
        /*** spinners **/
        binding.spinnerParcelType.adapter = materialAdapter
        binding.spinnerWeight.adapter = weightAdapter
        binding.spinnerWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parcelWeight = parcelWeights[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.spinnerParcelType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    parcelType = parcelTypes[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {


                }

            }
        binding.spinnerWeight.setSelection(weightAdapter.getPosition(modifyAdsArgs.weight))
        binding.spinnerParcelType.setSelection(
            materialAdapter.getPosition(
                Material(
                    modifyAdsArgs.type,
                    R.drawable.box_blue
                )
            )
        )

        /** buttons **/
        binding.back.setOnClickListener {
            val action =
                ModifyAdsFragmentDirections.actionModifyAdsFragmentToChatRoomFragment(modifyAdsArgs.toId)
            findNavController().navigate(action)
        }
        binding.uploadImage.setOnClickListener {
            openCamera()
        }
        binding.plus.setOnClickListener {
            val value: String? = binding.txBonus.text.toString()

            if (!value.isNullOrEmpty()) {
                var result = value.toInt()
                result += 1
                binding.txBonus.setText(result.toString())
            }


        }
        binding.minus.setOnClickListener {
            val value: String? = binding.txBonus.text.toString()

            if (!value.isNullOrEmpty()) {
                var result = value.toInt()
                if (result > 0) {
                    result -= 1
                    binding.txBonus.setText(result.toString())
                }


            }

        }
        binding.publish.isEnabled = false
        /*** adapters **/
        val placeAdapter =
            ArrayAdapter(binding.departure.context, R.layout.item_spinner_places, places)
        binding.departure.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.radius_dropdown
            )
        )
        binding.destination.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.radius_dropdown
            )
        )
        binding.departure.setAdapter(placeAdapter)
        binding.destination.setAdapter(placeAdapter)

    }

    private fun openCamera() {
        getUriFromCamera.launch(Intent(requireActivity(), CameraActivity::class.java))
    }

    private fun modifyParcel() {
        binding.publish.isEnabled = false
        binding.consToHide.visibility = View.VISIBLE
        coordinateBtnAndInputs(
            binding.publish,
            binding.departure,
            binding.destination,
            binding.txBonus,
            binding.description
        )

        binding.publish.setOnClickListener {
            builderLoading.showDialog("loading")
            val bonus = binding.txBonus.text.toString()
            val description = binding.description.text.toString()
            imageUri?.let { imageUri ->
                viewmodel.postParcelImage(imageUri)

            }

        }
    }


}