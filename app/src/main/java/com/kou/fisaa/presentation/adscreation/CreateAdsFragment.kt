package com.kou.fisaa.presentation.adscreation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appexecutors.picker.Picker
import com.appexecutors.picker.Picker.Companion.PICKED_MEDIA_LIST
import com.appexecutors.picker.Picker.Companion.REQUEST_CODE_PICKER
import com.appexecutors.picker.utils.PickerOptions
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.databinding.FragmentCreateAdsBinding
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateAdsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateAdsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mPickerOptions: PickerOptions

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

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICKER) {
            val mImageList =
                data?.getStringArrayListExtra(PICKED_MEDIA_LIST) as ArrayList //List of selected/captured images/videos
            mImageList.map {
                Log.d("tsawry", it)
            }
        }
    }


    private fun setupUi() {
        binding.rdParcel.setOnClickListener(this)
        binding.rdTransport.setOnClickListener(this)
        binding.rdTravel.setOnClickListener(this)
        binding.spinnerParcelType.adapter =
            CustomAdapter(requireActivity(), arrayListOf(m1, m2, m3))
        binding.edDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDate)
        }
        //TODO injectable
        mPickerOptions.apply {
            maxCount = 1                        //maximum number of images/videos to be picked
            allowFrontCamera = true             //allow front camera use
            excludeVideos = true               //exclude or include video functionalities
        }
        binding.rxUploadImage.setOnClickListener {
            Picker.startPicker(requireActivity(), mPickerOptions)
        }
    }


    override fun onClick(view: View?) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                binding.rdParcel.id -> {

                    if (checked) {
                        Toast.makeText(requireActivity(), "Parcel", Toast.LENGTH_SHORT).show()
                    }

                }

                binding.rdTravel.id -> {
                    if (checked) {
                        Toast.makeText(requireActivity(), "travel", Toast.LENGTH_SHORT).show()

                    }
                }

                binding.rdTransport.id -> {
                    if (checked) {
                        Toast.makeText(requireActivity(), "Transport", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }
}








