package com.nivin.payment.data.source.api

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.domain.source.Api
import javax.inject.Inject

/**
 * Implementation of API with retrofit
 */
class ApiWithRetrofit @Inject constructor(
    private val retrofitService: RetrofitService
) : Api {
    override suspend fun getPaymentDetails():
            Response<PaymentDetailsResponse> = retrofitService.getPaymentDetails()
}