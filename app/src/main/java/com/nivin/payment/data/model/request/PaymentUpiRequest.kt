package com.nivin.payment.data.model.request

/**
 * The request object for UPI payments
 */
data class PaymentUpiRequest(
    val virtualId: String,
    val merchantName: String,
    val amount: String,
    val upiAppPackageName: String,
    val note: String,
    val currencyCode: String
)