package com.nivin.payment.data.repository.implementation

import com.nivin.payment.common.errors.EmptyDataException
import com.nivin.payment.common.util.Response
import com.nivin.payment.domain.repository.EncryptorRepository
import com.nivin.payment.domain.source.ConstantsProvider
import com.nivin.payment.domain.source.Encryptor
import javax.inject.Inject

/**
 * Implementation of Encryptor
 */
class EncryptorRepositoryImpl @Inject constructor(
    private val encryptor: Encryptor,
    private val constantsProvider: ConstantsProvider
):EncryptorRepository{

    /**
     * encrypts the provided value and returns the encrypted value
     */
    override suspend fun encrypt(value: String): Response<String>{
        return when(val encryptionKeyResponse = constantsProvider.getEncryptionKey()){
            is Response.Error -> encryptionKeyResponse
            is Response.Loading -> encryptionKeyResponse
            is Response.Success -> {
                if(encryptionKeyResponse.data == null)Response.Error(EmptyDataException(String::class))
                else encryptor.encrypt(value,encryptionKeyResponse.data)
            }
        }
    }

    /**
     * decrypts the provided value and returns the decrypted value
     */
    override suspend fun decrypt(encryptedValue: String): Response<String>{
        return when(val encryptionKeyResponse = constantsProvider.getEncryptionKey()){
            is Response.Error -> encryptionKeyResponse
            is Response.Loading -> encryptionKeyResponse
            is Response.Success -> {
                if(encryptionKeyResponse.data == null)Response.Error(EmptyDataException(String::class))
                else encryptor.decrypt(encryptedValue,encryptionKeyResponse.data)
            }
        }
    }
}