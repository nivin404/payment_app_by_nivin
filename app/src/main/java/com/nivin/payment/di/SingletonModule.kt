package com.nivin.payment.di

import com.nivin.payment.common.util.Constants.Urls.BASE_URL
import com.nivin.payment.data.repository.dummy.DataRepositoryWithDummy
import com.nivin.payment.data.repository.implementation.EncryptorRepositoryImpl
import com.nivin.payment.data.source.api.ApiWithDummy
import com.nivin.payment.data.source.api.ApiWithRetrofit
import com.nivin.payment.data.source.api.RetrofitService
import com.nivin.payment.data.source.constants.ConstantsProviderWithBuildConfig
import com.nivin.payment.data.source.encyptor.EncryptorWithXor
import com.nivin.payment.domain.repository.DataRepository
import com.nivin.payment.domain.repository.EncryptorRepository
import com.nivin.payment.domain.source.Api
import com.nivin.payment.domain.source.ConstantsProvider
import com.nivin.payment.domain.source.Encryptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitService= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)


    @Provides
    @Singleton
    fun provideDataRepository(api: Api):DataRepository = DataRepositoryWithDummy()

    @Provides
    @Singleton
    fun provideEncryptorRepository(encryptor: Encryptor, constantsProvider: ConstantsProvider)
    :EncryptorRepository = EncryptorRepositoryImpl(encryptor,constantsProvider)

    @Provides
    @Singleton
    fun provideRemoteDataSource(retrofitService: RetrofitService):Api = ApiWithDummy()

    @Provides
    @Singleton
    fun provideEncryptor():Encryptor = EncryptorWithXor()

    @Provides
    @Singleton
    fun provideConstantsProvider():ConstantsProvider = ConstantsProviderWithBuildConfig()
}