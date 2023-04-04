package com.nivin.payment.data.repository.implementation

import com.nivin.payment.common.errors.EmptyDataException
import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.data.model.response.UserCredentialsResponse
import com.nivin.payment.domain.repository.AuthRepository
import com.nivin.payment.domain.repository.PaymentRepository
import com.nivin.payment.domain.source.Api
import com.nivin.payment.domain.source.payment.PaymentRazorPay
import com.nivin.payment.domain.source.payment.PaymentUpi
import javax.inject.Inject

/**
 * Implementation of payment repository
 */
class PaymentRepositoryImpl @Inject constructor(
    private val api:Api,
    private val authRepository: AuthRepository,
    private val paymentRazorPay: PaymentRazorPay,
    private val paymentUpi: PaymentUpi
):PaymentRepository{
    /**
     * Gets the payment details
     */
    override suspend fun getPaymentDetails(): Response<PaymentDetailsResponse>{
        return when(val response = authRepository.getUserId()){
            is Response.Error -> Response.Error(response.error)
            is Response.Loading -> Response.Loading(response.progress)
            is Response.Success -> {
                if(response.data == null)Response.Error(EmptyDataException(UserCredentialsResponse::class))
                else api.getPaymentDetails()
            }
        }
    }

    /**
     * Pays through razor pay
     */
    override suspend fun payThroughRazorPay() {
       //todo
    }

    /**
     * Pays through UPI
     */
    override suspend fun payThroughUpi() {
        //todo
    }
}