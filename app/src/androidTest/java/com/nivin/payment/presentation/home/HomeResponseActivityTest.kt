package com.nivin.payment.presentation.home

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.Assert.assertEquals

internal class HomeResponseActivityTest {

    @BeforeEach
    fun setUp() {
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun openUserPageTest(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("fasjflas",true)
    }

    @AfterEach
    fun tearDown() {
    }
}