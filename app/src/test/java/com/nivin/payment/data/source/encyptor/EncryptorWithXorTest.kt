package com.nivin.payment.data.source.encyptor

import android.provider.Contacts.SettingsColumns.KEY
import com.nivin.payment.common.util.Response
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized.Parameters


internal class EncryptorWithXorTest {
    @Test
    @Parameters
    fun encrypt_valid_key_value() {
        runBlocking(Dispatchers.Default){
            val KEY = "ThisIsAKey"
            val VALUE = "ThisIsValue"
            val encryptor = EncryptorWithXor()
            val encryptedValue = encryptor.encrypt(VALUE, KEY)
            encryptedValue as Response.Success
            val decryptedValue = encryptor.decrypt(encryptedValue.data!!, KEY)
            decryptedValue as Response.Success
            Assert.assertEquals(VALUE,decryptedValue.data)
        }
    }


}