package com.nivin.payment.data.source.encyptor

import com.nivin.payment.common.util.Response
import com.nivin.payment.domain.source.Encryptor
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Impl of Encryptor with XOR cipher
 */
class EncryptorWithXor : Encryptor {

    override suspend fun encrypt(value: String, key: String): Response<String> {
        val encryptedValue: Deferred<String> = withContext(Dispatchers.Default) {
            async {
                val valueArray: CharArray = value.toCharArray()
                val keyArray: CharArray = key.toCharArray()
                val builder: StringBuilder = StringBuilder()
                var keyIndex = 0;
                for (index in 0..valueArray.lastIndex) {
                    val intValue: Int = valueArray[index].toInt() xor keyArray[keyIndex].toInt()
                    val hexString = String.format("%02x", intValue)
                    builder.append(hexString)
                    keyIndex++;
                    if (keyIndex > keyArray.lastIndex) keyIndex = 0
                }
                builder.toString()
            }
        }
        return withContext(Dispatchers.Unconfined) { Response.Success(encryptedValue.await()) }
    }

    override suspend fun decrypt(encryptedValue: String, key: String): Response<String> {
        val deferredValue: Deferred<String> = withContext(Dispatchers.IO) {
            async {
                val encryptedBuilder = StringBuilder(encryptedValue)
                val hexToDeciBuilder = StringBuilder()
                for (index in 0 until encryptedBuilder.lastIndex step 2) {
                    val hexString: String = encryptedBuilder.substring(index, index + 2)
                    val intValue: Int = hexString.toInt(16)
                    hexToDeciBuilder.append(intValue.toChar())
                }
                val decimalArray: CharArray = hexToDeciBuilder.toString().toCharArray()
                val keyArray: CharArray = key.toCharArray()
                var keyIndex = 0
                val valueBuilder = StringBuilder()
                for (index in 0..decimalArray.lastIndex) {
                    val intValue:Int = decimalArray[index].toInt() xor keyArray[keyIndex].toInt()
                    valueBuilder.append(intValue.toChar())
                    keyIndex++
                    if (keyIndex > keyArray.lastIndex) keyIndex = 0
                }
                valueBuilder.toString()
            }
        }
        return withContext(Dispatchers.Unconfined) { Response.Success(deferredValue.await()) }
    }
}