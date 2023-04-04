package com.nivin.payment.di

import com.nivin.payment.data.repository.dummy.AuthRepositoryWithDummy
import com.nivin.payment.data.repository.dummy.DataRepositoryWithDummy
import com.nivin.payment.domain.repository.AuthRepository
import com.nivin.payment.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DummyProviderModule {

    /**
     * This Provides a dummy data repository, real repository is provided in [SingletonModule]
     */
    @Provides
    @Singleton
    fun provideDummyDataRepository(): DataRepository = DataRepositoryWithDummy()


    /**
     * This provides the Dummy authenticator, Add the real auth provider in [SingletonModule]
     */
    @Provides
    @Singleton
    fun provideAuthenticator(): AuthRepository {
        return AuthRepositoryWithDummy()
    }
}