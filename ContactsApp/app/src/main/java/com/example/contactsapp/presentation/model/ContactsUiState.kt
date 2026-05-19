package com.example.contactsapp.presentation.model

sealed interface ContactsUiState {
    data class Content(val data: List<ItemType>) : ContactsUiState
    data object Error : ContactsUiState
    data object PermissionDenied : ContactsUiState
    data object Loading : ContactsUiState
}
