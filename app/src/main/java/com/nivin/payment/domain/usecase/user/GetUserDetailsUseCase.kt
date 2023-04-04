package com.nivin.payment.domain.usecase.user

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.UserCredentialsResponse
import com.nivin.payment.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke(): Flow<Response<UserCredentialsResponse>> = flow {
        emit(authRepository.getUserId())
    }
}