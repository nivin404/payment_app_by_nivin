package com.nivin.payment.data.repository.dummy

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.UserCredentialsResponse
import com.nivin.payment.domain.repository.AuthRepository

/**
 * Dummy repository to provide authentication
 */
class AuthRepositoryWithDummy : AuthRepository{
    override suspend fun getUserId(): Response<UserCredentialsResponse> {
        return  Response.Success(UserCredentialsResponse(12345))
    }
}