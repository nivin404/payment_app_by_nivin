package com.nivin.payment.domain.source.payment

import androidx.appcompat.app.AppCompatActivity
import com.nivin.payment.data.model.request.PaymentUpiRequest

interface PaymentUpi {
    fun requestPayment(activity:AppCompatActivity,paymentUpiRequest: PaymentUpiRequest)
}