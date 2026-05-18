package com.example.contactsapp.data

import androidx.core.net.toUri
import com.example.contactsapp.data.dto.ContactDto
import com.example.contactsapp.data.local.ContactsDataSource
import com.example.contactsapp.ui.model.ContactUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsDataSource: ContactsDataSource
) {
    fun getContacts(): Flow<List<ContactUiModel>> =
        contactsDataSource.getContacts().map { contactsDto ->
            contactsDto
                .sortedBy { it.contactDisplayName }
                .map { it.toUiModel() }
        }
}

fun ContactDto.toUiModel() = ContactUiModel(
    contactId,
    contactDisplayName,
    phoneNumber,
    contactAvatarUri?.toUri()
)