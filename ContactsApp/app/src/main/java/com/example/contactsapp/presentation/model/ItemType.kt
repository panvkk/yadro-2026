package com.example.contactsapp.presentation.model

interface ItemType {
    data class LetterHeader(val letter: Char) : ItemType
    data class ContactCard(val contact: ContactUiModel) : ItemType
}