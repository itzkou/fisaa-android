package com.kou.fisaa.presentation.adscreation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Material
import com.kou.fisaa.databinding.FragmentCreateAdsBinding
import com.kou.fisaa.presentation.camera.CameraActivity
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.CustomAdapter
import com.kou.fisaa.utils.coordinateBtnAndInputs

class CreateAdsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateAdsBinding? = null
    private val binding get() = _binding!!


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
            startActivity(Intent(activity, CameraActivity::class.java))
        }

    }


    override fun onClick(view: View?) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                binding.rdParcel.id -> {

                    if (checked) {
                        binding.publish.isEnabled = false
                        binding.consToHide.visibility = View.VISIBLE
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








