package com.kou.fisaa.data.remote

import android.util.Log
import com.kou.fisaa.utils.Resource
import retrofit2.Response

abstract class RetrofitSource {
    protected suspend fun <T> getResource(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("responsi", message)
        return Resource.error(message)
    }
}