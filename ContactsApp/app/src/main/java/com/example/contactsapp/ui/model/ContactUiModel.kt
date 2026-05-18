package com.example.contactsapp.ui.model

import android.net.Uri

data class ContactUiModel(
    val id: Long,
    val fullName: String,
    val phoneNumber: String,
    val avatarUri: Uri?
)
