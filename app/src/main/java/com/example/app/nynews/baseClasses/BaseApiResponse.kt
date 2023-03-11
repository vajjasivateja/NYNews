package com.example.app.nynews.baseClasses

import android.content.Context
import com.example.app.nynews.ResponseStates
import com.example.app.nynews.dataClasses.apiServices.NetworkHelper
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(context: Context, apiCall: suspend () -> Response<T>): ResponseStates<T> {
        try {
            if (NetworkHelper(context).hasInternetConnection()) {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { return ResponseStates.Success(body) }
                }
                return error(response.code(), "Error Code: ${response.code()}" + "\n" + response.message().toString())
            } else {
                return error(null, "No Internet connection")
            }
        } catch (ex: Exception) {
            return error(null, ex.message ?: ex.toString())
        }
    }

    private fun <T> error(code: Int?, errorMessage: String): ResponseStates<T> = ResponseStates.Failure(code, errorMessage)
}