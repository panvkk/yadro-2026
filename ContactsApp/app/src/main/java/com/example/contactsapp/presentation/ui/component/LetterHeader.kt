package com.example.contactsapp.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.contactsapp.R

@Composable
fun LetterHeader(
    letter: Char,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        HorizontalDivider(
            thickness = dimensionResource(R.dimen.divider_thickness),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            text = "$letter",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.large_padding))
        )
    }
}