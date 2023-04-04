package com.nivin.payment.domain.source

import com.nivin.payment.common.util.Response

interface Encryptor {
   suspend fun encrypt(value:String, key:String): Response<String>
   suspend fun decrypt(encryptedValue:String, key:String): Response<String>
}