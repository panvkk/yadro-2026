package com.example.contactsapp.data.dto

data class ContactDto(
    val contactId: Long,
    val contactDisplayName: String,
    val phoneNumber: String,
    val isMobile: Boolean,
    val contactAvatarUri: String?
)