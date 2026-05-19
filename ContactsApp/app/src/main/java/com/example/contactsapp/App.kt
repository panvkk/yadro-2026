package com.example.contactsapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.contactsapp.presentation.ui.component.ContactsAppTopBar
import com.example.contactsapp.presentation.ui.screen.ContactsScreen
import com.example.contactsapp.presentation.viewmodel.ContactsViewModel


@Composable
fun ContactsApp(
    contactsViewModel: ContactsViewModel,
    makeCall: (String) -> Unit
) {
    Scaffold(
        topBar = {
            ContactsAppTopBar()
        }
    ) { innerPadding ->
        ContactsScreen(
            viewModel = contactsViewModel,
            makeCall = makeCall,
            modifier = Modifier.padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}