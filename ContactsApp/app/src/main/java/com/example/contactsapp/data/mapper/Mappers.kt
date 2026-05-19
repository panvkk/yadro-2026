package com.example.contactsapp.data.mapper

import com.example.contactsapp.data.dto.ContactDto
import com.example.contactsapp.domain.model.Contact

fun ContactDto.toDomain() = Contact(contactId, contactDisplayName, phoneNumber, contactAvatarUri)