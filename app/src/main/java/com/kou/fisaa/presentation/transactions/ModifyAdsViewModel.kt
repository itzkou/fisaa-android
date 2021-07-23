package com.kou.fisaa.presentation.transactions

import androidx.lifecycle.ViewModel
import com.kou.fisaa.data.repository.FisaaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ModifyAdsViewModel @Inject constructor(private val repository: FisaaRepository) :
    ViewModel()