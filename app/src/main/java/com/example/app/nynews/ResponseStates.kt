package com.example.app.nynews

sealed class ResponseStates<out T> {
    object Loading : ResponseStates<Nothing>()
    object Empty : ResponseStates<Nothing>()
    data class Success<out T>(val data: T) : ResponseStates<T>()
    data class Failure(val code:Int?, val errorMessage: String?) : ResponseStates<Nothing>()
}