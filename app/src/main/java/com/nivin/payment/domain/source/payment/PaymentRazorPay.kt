package com.nivin.payment.domain.source.payment

import androidx.appcompat.app.AppCompatActivity
import com.nivin.payment.data.model.response.RazorPayKeysResponse

interface PaymentRazorPay {
    fun requestPayment(paymentDetailsResponse: RazorPayKeysResponse, activity: AppCompatActivity)

}