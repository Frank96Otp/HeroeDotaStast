package com.example.dotaherostats.data.response

import java.lang.Exception;


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
}