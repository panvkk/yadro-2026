package com.example.contactsapp.data.di

import com.example.contactsapp.data.ContactsRepositoryImpl
import com.example.contactsapp.domain.repository.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl) : ContactsRepository
}