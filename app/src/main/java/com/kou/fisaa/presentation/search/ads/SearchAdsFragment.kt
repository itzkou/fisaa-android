package com.kou.fisaa.presentation.search.ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import com.kou.fisaa.R
import com.kou.fisaa.data.entities.Star
import com.kou.fisaa.databinding.FragmentSearchAdBinding
import com.kou.fisaa.utils.BuilderDatePicker
import com.kou.fisaa.utils.coordinateBtnAndInputs
import com.kou.fisaa.utils.toast
import java.text.NumberFormat
import java.util.*

class SearchAdsFragment : Fragment() {

    private var _binding: FragmentSearchAdBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAdBinding.inflate(inflater, container, false)
        val view = binding.root

        setupUi()
        searchAds()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupUi() {
        coordinateBtnAndInputs(
            binding.btnSearchFilter,
            binding.edArrivalDate,
            binding.edDepDate,
            binding.departureSearch,
            binding.arrivalSearch
        )
        val startTwo = Star()
        val starThree = Star()
        val starFour = Star()
        val starFive = Star()
        val notRated = Star()
        val fourAndFive = Star()
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
        binding.two.setBackgroundResource(R.drawable.round_star_d)
        binding.three.setBackgroundResource(R.drawable.round_star_d)
        binding.four.setBackgroundResource(R.drawable.round_star_d)
        binding.five.setBackgroundResource(R.drawable.round_star_d)
        binding.fiveAndFour.setBackgroundResource(R.drawable.round_star_d)
        binding.none.setBackgroundResource(R.drawable.round_star_d)
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.edArrivalDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edArrivalDate)
        }
        binding.edDepDate.setOnClickListener {
            BuilderDatePicker.showDialog(requireActivity(), binding.edDepDate)

        }


        cards(binding.two, binding.tvTwo, startTwo)
        cards(binding.three, binding.tvThree, starThree)
        cards(binding.four, binding.tvFour, starFour)
        cards(binding.five, binding.tvFive, starFive)
        cards(binding.none, binding.tvNone, notRated)
        cards(binding.fiveAndFour, binding.tvFiveFour, fourAndFive)


    }

    private fun cards(card: CardView, tx: TextView, star: Star) {

        card.setOnClickListener {

            star.isClicked = if (!star.isClicked) {
                tx.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                card.setBackgroundResource(R.drawable.round_star)
                true
            } else {
                card.setBackgroundResource(R.drawable.round_star_d)
                tx.setTextColor(ContextCompat.getColor(requireActivity(), R.color.darkoGrey))
                false

            }
        }

    }

    //Todo add notation and tarif
    private fun searchAds() {
        binding.btnSearchFilter.setOnClickListener {
            val arrivalDate = binding.edArrivalDate.text.toString()
            val departureDate = binding.edDepDate.text.toString()
            val departure = binding.departureSearch.text.toString()
            val destination = binding.arrivalSearch.text.toString()
            val action = SearchAdsFragmentDirections.actionSearchAdsFragmentToAds(
                departureDate,
                arrivalDate,
                departure,
                destination, source = "search"
            )
            findNavController().navigate(action)

        }
    }


}