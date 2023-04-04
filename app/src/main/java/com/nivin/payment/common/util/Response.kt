package com.nivin.payment.common.util

/**
 * A single class to handle all three of the response cases, this can be used to request
 * any type of responses
 */
sealed class Response<T>(){
    data class Success<T>(val data:T? = null):Response<T>()
    data class Loading<T>(val progress:Int? = null):Response<T>()
    data class Error<T>(val error:Exception? = null):Response<T>()
}
