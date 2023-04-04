package com.nivin.payment.data.source.api

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.domain.source.Api

/**
 * Provides dummy for API
 */
class ApiWithDummy : Api {
    override suspend fun getPaymentDetails(): Response<PaymentDetailsResponse> = Response.Success(
        PaymentDetailsResponse(
            "100",
            "Payment for test",
            "INR",
            "virtualUPIId",
            "Merchant Name"
        )
    )
}