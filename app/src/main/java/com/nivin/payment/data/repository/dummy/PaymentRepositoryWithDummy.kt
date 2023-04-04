package com.nivin.payment.data.repository.dummy

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.domain.repository.PaymentRepository

/**
 * Dummy repository to provide payment related data
 */
class PaymentRepositoryWithDummy :PaymentRepository{
    override suspend fun getPaymentDetails(): Response<PaymentDetailsResponse> {
        return Response.Success(PaymentDetailsResponse(
            "100",
            "Payment for test",
            "INR",
            "nk0075342@okicici",
            "Merchand Nivin"))
    }

    override suspend fun payThroughRazorPay() {
        //todo
    }

    override suspend fun payThroughUpi() {
        //todo
    }
}