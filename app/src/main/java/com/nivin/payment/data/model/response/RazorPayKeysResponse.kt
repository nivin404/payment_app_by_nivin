package com.nivin.payment.data.model.response

/**
 * The response object that contains the data to be passed to the Razor pay API
 */
data class RazorPayKeysResponse(
    val apiKey: String, val orderId: String,
    val amount: String,
    val currency:String,
    val companyName:String,
    val description:String,
    val userPreEmail:String,
    val userPrePhone:String
)