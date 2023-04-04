package com.nivin.payment.domain.repository

import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.response.UserCredentialsResponse

interface AuthRepository {
    suspend fun getUserId():Response<UserCredentialsResponse>
}
