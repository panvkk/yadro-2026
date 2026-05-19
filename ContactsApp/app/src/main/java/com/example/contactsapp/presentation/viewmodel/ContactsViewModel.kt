package com.example.contactsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.core.AppDispatchers
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val dispatchers: AppDispatchers
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
                    val itemTypes = withContext(dispatchers.default) { // потому то сложный маппинг
                        getItemTypes(contacts.map { it.toUiModel() })
                    }
                    _uiState.update { ContactsUiState.Content(itemTypes) }
                }
                .onFailure {
                    _uiState.update { ContactsUiState.Error }
                    Log.e(TAG, it.message ?: "Unknown error.")
                }
        }
    }

    private fun getItemTypes(contacts: List<ContactUiModel>) : List<ItemType> {
        if(contacts.isEmpty()) return emptyList()

        val getFirstLetter = { contact: ContactUiModel ->
            if(contact.fullName.isEmpty())
                contact.phoneNumber.first()
            else
                contact.fullName.first().uppercaseChar()
        }

        val itemTypes = mutableListOf<ItemType>()
        var previousLetter = getFirstLetter(contacts.first())
        itemTypes.add(ItemType.LetterHeader(previousLetter))

        contacts.forEach {
            val currentLetter = getFirstLetter(it)

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
        private const val TAG = "ContactsViewModel"
    }
}