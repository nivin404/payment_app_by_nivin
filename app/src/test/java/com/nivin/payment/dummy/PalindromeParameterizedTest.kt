package com.nivin.payment.dummy

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(value = Parameterized::class)
class PalindromeParameterizedTest(private val value:String, private val expect:Boolean){
    @Test
    fun isPalindromeTest(){
        assert(value.toBoolean() != expect)
    }

    companion object {
        @JvmStatic
        @Parameters(name = "{index}:{0} is palindrome {1}")
        fun getParams(): List<Array<Any>> {
            return listOf<Array<Any>>(
                arrayOf("fasd", false),
                arrayOf("malayalam",true)
            )
        }
    }
}