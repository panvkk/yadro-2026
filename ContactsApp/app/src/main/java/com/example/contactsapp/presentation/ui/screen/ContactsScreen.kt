package com.example.contactsapp.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactsapp.R
import com.example.contactsapp.presentation.ui.component.ContactCard
import com.example.contactsapp.presentation.model.ContactsUiState
import com.example.contactsapp.presentation.model.ItemType
import com.example.contactsapp.presentation.ui.component.LetterHeader
import com.example.contactsapp.presentation.ui.component.NoPermissionToContactsPlaceholder
import com.example.contactsapp.presentation.ui.component.PermissionDeniedAlertDialog
import com.example.contactsapp.presentation.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    makeCall: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val alertDialogsState = viewModel.alertDialogsState.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is ContactsUiState.Content -> {
                items(
                    state.data,
                    contentType = { item ->
                        when(item) {
                            is ItemType.LetterHeader -> "LETTER_HEADER_TYPE"
                            is ItemType.ContactCard -> "CONTACT_TYPE"
                        }
                    }
                ) { item ->
                    when(item) {
                        is ItemType.ContactCard -> {
                            ContactCard(
                                contact = item.contact,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimensionResource(R.dimen.small_padding))
                                    .clickable { makeCall(item.contact.phoneNumber) }
                            )
                        }
                        is ItemType.LetterHeader ->
                            LetterHeader(item.letter, Modifier.padding(vertical = dimensionResource(R.dimen.large_padding)))
                    }

                }
            }
            is ContactsUiState.PermissionDenied -> {
                item { NoPermissionToContactsPlaceholder() }
            }
            else -> {  }
        }
    }
    if(alertDialogsState.showContactsAlertDialog) {
        PermissionDeniedAlertDialog(
            onDismiss = { viewModel.closeContactsAlertDialog() },
            text = stringResource(R.string.read_contacts_permission_denied_alert_dialog_text)
        )
    } else if(alertDialogsState.showCallsAlertDialog) {
        PermissionDeniedAlertDialog(
            onDismiss = { viewModel.closeCallsAlertDialog() },
            text = stringResource(R.string.call_phone_permission_denied_alert_dialog_text)
        )
    }
}