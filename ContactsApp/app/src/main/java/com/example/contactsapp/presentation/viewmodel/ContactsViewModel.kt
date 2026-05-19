package com.example.contactsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.domain.usecase.GetContactsUseCase
import com.example.contactsapp.presentation.mapper.toUiModel
import com.example.contactsapp.presentation.model.AlertDialogsModel
import com.example.contactsapp.presentation.model.ContactUiModel
import com.example.contactsapp.presentation.model.ContactsUiState
import com.example.contactsapp.presentation.model.ItemType
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

    private val _alertDialogsState = MutableStateFlow(AlertDialogsModel(
        showContactsAlertDialog = false,
        showCallsAlertDialog = false
    ))
    val alertDialogsState = _alertDialogsState.asStateFlow()

    fun loadContacts() {
        viewModelScope.launch {
            getContactsUseCase.invoke()
                .onSuccess { contacts ->
                    val itemTypes = getItemTypes(contacts.map { it.toUiModel() })
                    _uiState.update { ContactsUiState.Content(itemTypes) }
                }
                .onFailure {
                    _uiState.update { ContactsUiState.Error }
                    Log.e(TAG, it.message ?: "Unknown error.")
                }
        }
    }

    private fun getItemTypes(contacts: List<ContactUiModel>) : List<ItemType> {
        val itemTypes = mutableListOf<ItemType>()
        var previousLetter = contacts.first().fullName.first().uppercaseChar()
        itemTypes.add(ItemType.LetterHeader(previousLetter))

        contacts.forEach {
            val currentLetter = it.fullName.first().uppercaseChar()
            if(previousLetter != currentLetter) {
                itemTypes.add(ItemType.LetterHeader(currentLetter))
                previousLetter = currentLetter

            }
            itemTypes.add(ItemType.ContactCard(it))
        }
        return itemTypes.toList()
    }

    fun onPermissionDenied() {
        _uiState.update { ContactsUiState.PermissionDenied }
    }

    fun showContactsAlertDialog() {
        _alertDialogsState.update { it.copy(showContactsAlertDialog = true) }
    }
    fun showCallsAlertDialog() {
        _alertDialogsState.update { it.copy(showCallsAlertDialog = true) }
    }
    fun closeContactsAlertDialog() {
        _alertDialogsState.update { it.copy(showContactsAlertDialog = false) }
    }
    fun closeCallsAlertDialog() {
        _alertDialogsState.update { it.copy(showCallsAlertDialog = false) }
    }

    companion object {
        const val TAG = "ContactsViewModel"
    }
}