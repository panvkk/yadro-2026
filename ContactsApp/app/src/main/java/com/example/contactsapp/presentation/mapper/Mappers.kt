package com.example.contactsapp.presentation.mapper

import androidx.core.net.toUri
import com.example.contactsapp.domain.model.Contact
import com.example.contactsapp.presentation.model.ContactUiModel

fun Contact.toUiModel() = ContactUiModel(id, displayName, phoneNumber, contactAvatarUri?.toUri())