package com.nivin.payment.domain.repository

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse

interface PaymentRepository {
    suspend fun getPaymentDetails(): Response<PaymentDetailsResponse>
    suspend fun payThroughRazorPay()
    suspend fun payThroughUpi()
}