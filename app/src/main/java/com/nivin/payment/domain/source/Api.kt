package com.nivin.payment.domain.source

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse

interface Api {
    suspend fun getPaymentDetails(): Response<PaymentDetailsResponse>
}