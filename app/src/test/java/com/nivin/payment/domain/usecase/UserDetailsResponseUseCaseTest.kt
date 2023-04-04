package com.nivin.payment.domain.usecase

import com.nivin.payment.domain.usecase.user.GetUserDetailsUseCase
import kotlinx.coroutines.flow.onEach
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject


internal class UserDetailsResponseUseCaseTest {
    @Inject lateinit var getUserDetailsUseCase: GetUserDetailsUseCase
    @Test
    fun invoke_userDetailsUseCase_input_UserDetails() {
        // arrange
        getUserDetailsUseCase().onEach {

        }
        // act
        assert(false)
        // assert
    }
    @Before
    fun setUp(){
        print("Setting up UserDetailsUseCase")
    }
    @After
    fun tearDown(){
       print("Finishing test of UserDetailsUseCase")
    }

}