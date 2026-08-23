package com.example.rigcraft.di

import com.example.rigcraft.data.repository.AuthRepositoryImpl
import com.example.rigcraft.data.repository.SeederRepositoryImpl
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.SeederRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSeederRepository(
        impl: SeederRepositoryImpl
    ): SeederRepository
}