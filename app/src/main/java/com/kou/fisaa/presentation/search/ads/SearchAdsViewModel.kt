package com.kou.fisaa.presentation.search.ads

import androidx.lifecycle.ViewModel
import com.kou.fisaa.data.repository.FisaaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchAdsViewModel @Inject constructor(repository: FisaaRepository) : ViewModel()