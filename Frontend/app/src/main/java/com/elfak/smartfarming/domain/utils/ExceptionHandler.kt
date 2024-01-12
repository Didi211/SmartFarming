package com.elfak.smartfarming.domain.utils

import com.elfak.smartfarming.data.models.api.ApiResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ExceptionHandler {
    companion object {
        fun handleApiCallException(ex: Exception): ApiResponse {
            return when (ex) {
                is ConnectException -> throw Exception("Can't send request. Check the internet connection.")
                is SocketTimeoutException -> throw Exception("Server unavailable. Check your network or try again later.")
                is HttpException -> {
                    val errorBody = ex.response()!!.errorBody()!!.string()
                    Gson().fromJson(errorBody, ApiResponse::class.java)
                }
                else -> throw ex
            }
        }

        fun throwApiResponseException(ex: ApiResponse) {
            if (ex.details == null) {
                throw Exception(ex.message)
            }
            throw Exception("${ex.message} - ${ex.details}")
        }
    }
}