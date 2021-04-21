package com.kou.fisaa.presentation.adscreation

import androidx.lifecycle.ViewModel
import com.kou.fisaa.data.repository.FisaaRepositoryAbstraction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAdsViewModel @Inject constructor(private val repository: FisaaRepositoryAbstraction) :
    ViewModel()