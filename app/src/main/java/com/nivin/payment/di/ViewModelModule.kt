package com.nivin.payment.di

import com.nivin.payment.data.repository.dummy.PaymentRepositoryWithDummy
import com.nivin.payment.data.repository.implementation.PaymentRepositoryImpl
import com.nivin.payment.data.source.payment.PaymentRazorPayImpl
import com.nivin.payment.data.source.payment.PaymentUpiImpl
import com.nivin.payment.domain.repository.AuthRepository
import com.nivin.payment.domain.repository.PaymentRepository
import com.nivin.payment.domain.source.Api
import com.nivin.payment.domain.source.payment.PaymentRazorPay
import com.nivin.payment.domain.source.payment.PaymentUpi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun providePaymentRepository(api: Api, authRepository: AuthRepository,
                                 paymentRepository: PaymentRazorPay,paymentUpi: PaymentUpi):
            PaymentRepository = PaymentRepositoryImpl(api,authRepository,paymentRepository,paymentUpi)
    @Provides
    fun providePaymentRazorPay():PaymentRazorPay = PaymentRazorPayImpl()

    @Provides
    fun providePaymentUpi():PaymentUpi = PaymentUpiImpl()
}