package com.nivin.payment.data.source.constants

import com.nivin.nivinecommerceapp.BuildConfig
import com.nivin.payment.common.util.Response
import com.nivin.payment.domain.source.ConstantsProvider

/**
 * Implementation of ConstantsProvider with BuildConfig
 */
class ConstantsProviderWithBuildConfig :ConstantsProvider{
    override suspend fun getEncryptionKey(): Response<String> {
        return Response.Success(BuildConfig.ENCRYPTION_KEY)
    }
}