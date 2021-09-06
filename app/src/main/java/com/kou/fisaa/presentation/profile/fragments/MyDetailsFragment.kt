package com.kou.fisaa.presentation.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import coil.load
import com.kou.fisaa.R
import com.kou.fisaa.databinding.FragmentMyDetailsBinding
import com.kou.fisaa.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDetailsFragment : Fragment() {
    private var _binding: FragmentMyDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyDetailsViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
    private var id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, { resUser ->
            when (resUser.status) {
                Resource.Status.SUCCESS -> {
                    val user = resUser.data
                    user?.let {
                        id = it._id
                        binding.picture.load(it.image)
                        binding.txBio.text = it.description
                        binding.txName.text = it.firstName + " " + it.lastName
                    }
                }
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireActivity(), "error loading profile", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        id?.let {
            viewModel.showMyFlights(it)
        }
        viewModel.myFlights.observe(viewLifecycleOwner, { flights ->
            binding.tripsCount.text = flights.size.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}