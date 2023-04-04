package com.nivin.payment.data.source.api

import com.nivin.payment.common.util.Constants
import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.data.model.response.UserCredentialsResponse
import retrofit2.http.GET

/**
 * Interface that to be provided to the retrofit
 */
interface RetrofitService {
    @GET(Constants.Urls.userCredentials)
    suspend fun getUserDetails():Response<UserCredentialsResponse>

    @GET(Constants.Urls.paymentDetails)
    suspend fun getPaymentDetails(): Response<PaymentDetailsResponse>
}