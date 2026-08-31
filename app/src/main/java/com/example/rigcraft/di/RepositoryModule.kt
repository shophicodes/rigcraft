package com.example.rigcraft.di

import com.example.rigcraft.data.repository.AuthRepositoryImpl
import com.example.rigcraft.data.repository.CartRepositoryImpl
import com.example.rigcraft.data.repository.OrderRepositoryImpl
import com.example.rigcraft.data.repository.ProductRepositoryImpl
import com.example.rigcraft.data.repository.ProfileRepositoryImpl
import com.example.rigcraft.data.repository.SeederRepositoryImpl
import com.example.rigcraft.data.repository.WishlistRepositoryImpl
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.domain.repository.OrderRepository
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.domain.repository.SeederRepository
import com.example.rigcraft.domain.repository.WishlistRepository
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

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        impl: WishlistRepositoryImpl
    ): WishlistRepository
}