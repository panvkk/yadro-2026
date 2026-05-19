package com.example.contactsapp.domain.model

data class Contact(
    val id: Long,
    val displayName: String,
    val phoneNumber: String,
    val contactAvatarUri: String?
)
