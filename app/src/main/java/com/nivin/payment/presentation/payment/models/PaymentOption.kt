package com.nivin.payment.presentation.payment.models

internal data class PaymentOption(
    val name: String,
    val paymentType: PaymentType,
    val imgResId: Int
) : PaymentItem {
    var isSelected: Boolean = false
}