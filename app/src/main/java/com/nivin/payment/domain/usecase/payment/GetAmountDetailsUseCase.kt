package com.nivin.payment.domain.usecase.payment

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.domain.repository.AuthRepository
import com.nivin.payment.domain.repository.DataRepository
import com.nivin.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAmountDetailsUseCase @Inject constructor(private val paymentRepository: PaymentRepository){
    operator fun invoke():Flow<Response<PaymentDetailsResponse>> = flow {
        emit(paymentRepository.getPaymentDetails())
    }
}