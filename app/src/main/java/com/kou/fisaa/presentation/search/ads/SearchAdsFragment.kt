package com.kou.fisaa.presentation.search.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.slider.RangeSlider
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentSearchAdBinding
import com.kou.fisaa.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class SearchAdsFragment : Fragment() {

    private var _binding: FragmentSearchAdBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchAdsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAdBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUi()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        binding.rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("EUR")
            format.format(value.toDouble())
        }
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being started
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                // Responds to when slider's touch event is being stopped
            }
        })

        binding.rangeSlider.addOnChangeListener { rangeSlider, value, fromUser ->
            requireActivity().toast(value.toString())
        }
    }

}