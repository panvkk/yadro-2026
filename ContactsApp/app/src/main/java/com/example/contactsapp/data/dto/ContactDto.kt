package com.example.contactsapp.data.dto

data class ContactDto(
    val contactId: String,
    val contactDisplayName: String,
    val mainPhoneNumber: String,
    val contactAvatarUri: String?
)