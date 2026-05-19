package com.example.contactsapp.domain.usecase

import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.domain.repository.ContactsRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val repository: ContactsRepository
) {
    suspend operator fun invoke() : Result<List<Contact>> {
        return repository.getContacts()
            .fold(
                onSuccess = { contacts -> Result.success(contacts.sortedBy { it.displayName }) },
                onFailure = { e -> Result.failure(e) }
            )
    }
}