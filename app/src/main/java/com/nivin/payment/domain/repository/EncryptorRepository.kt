package com.nivin.payment.domain.repository

import com.nivin.payment.common.util.Response

interface EncryptorRepository {
    suspend fun encrypt(value:String): Response<String>
    suspend fun decrypt(encryptedValue:String): Response<String>
}