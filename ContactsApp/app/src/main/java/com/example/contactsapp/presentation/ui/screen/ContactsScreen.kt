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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.contactsapp.R
import com.example.contactsapp.presentation.ui.component.ContactCard
import com.example.contactsapp.presentation.model.ContactsUiState
import com.example.contactsapp.presentation.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    makeCall: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state) {
            is ContactsUiState.Content -> {
                items(state.data, { it.id }) { contact ->
                    ContactCard(
                        contact = contact,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.small_padding))
                            .clickable { makeCall(contact.phoneNumber) }
                    )
                }
            }
            else -> {  }
        }
    }
}