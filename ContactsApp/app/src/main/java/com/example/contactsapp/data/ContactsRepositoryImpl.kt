package com.example.contactsapp.data

import com.example.contactsapp.data.local.ContactsDataSource
import com.example.contactsapp.data.mapper.toDomain
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactsRepository
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val contactsDataSource: ContactsDataSource
) : ContactsRepository {
    override suspend fun getContacts(): Result<List<Contact>> =
        try {
            val contacts = contactsDataSource.getContacts().map { it.toDomain() }
            Result.success(contacts)
        } catch (e: Exception) {
            Result.failure(e)
        }
}