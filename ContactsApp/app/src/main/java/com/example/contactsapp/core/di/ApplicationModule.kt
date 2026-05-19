package com.example.contactsapp.core.di

import com.example.contactsapp.core.AppDispatchers
import com.example.contactsapp.core.DefaultAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideDispatchers() : AppDispatchers = DefaultAppDispatchers()
}