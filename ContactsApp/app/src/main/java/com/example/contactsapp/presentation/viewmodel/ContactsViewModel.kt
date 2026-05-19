package com.example.contactsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.usecase.GetContactsUseCase
import com.example.contactsapp.presentation.mapper.toUiModel
import com.example.contactsapp.presentation.model.ContactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ContactsUiState>(ContactsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadContacts() {
        viewModelScope.launch {
            val contacts = getContactsUseCase.invoke().map { it.toUiModel() }
            _uiState.update { ContactsUiState.Content(contacts) }
        }
    }
}