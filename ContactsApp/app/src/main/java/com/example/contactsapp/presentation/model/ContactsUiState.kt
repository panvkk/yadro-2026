package com.example.contactsapp.presentation.model

sealed interface ContactsUiState {
    data class Content(val data: List<ContactUiModel>) : ContactsUiState
    data class Error(val e: Throwable) : ContactsUiState
    data object Loading : ContactsUiState
}
