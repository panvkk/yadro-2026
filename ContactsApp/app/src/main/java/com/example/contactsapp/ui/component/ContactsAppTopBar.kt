package com.example.contactsapp.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.contactsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsAppTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.contacts_top_bar_title),
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding))
    )
}