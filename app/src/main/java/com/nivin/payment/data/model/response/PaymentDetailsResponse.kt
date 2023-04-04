package com.nivin.payment.data.model.response

/**
 * The response object for getting payment details from the server
 */
data class PaymentDetailsResponse(
    val amount: String,
    val note: String,
    val currencyCode: String,
    val virtualId: String,
    val merchantName: String
)