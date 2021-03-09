package com.kou.fisaa.data.repository

import com.kou.fisaa.data.local.FisaaDao
import com.kou.fisaa.data.remote.FisaaRemote
import javax.inject.Inject

class FisaaRepository @Inject constructor(
    private  val remote: FisaaRemote,
    private  val local :FisaaDao
) {


}