package com.nivin.payment.domain.source

import com.nivin.payment.common.util.Response

interface ConstantsProvider {
    suspend fun getEncryptionKey():Response<String>
}