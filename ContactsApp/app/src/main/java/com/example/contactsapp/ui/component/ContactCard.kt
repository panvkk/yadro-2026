package com.example.contactsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.example.contactsapp.R
import com.example.contactsapp.ui.model.ContactUiModel

@Composable
fun ContactCard(
    contact: ContactUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(dimensionResource(R.dimen.contact_card_height)),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen.large_padding))
                .fillMaxSize()
        ) {
            if(contact.avatarUri == null) {
                Image(
                    painter = painterResource(R.drawable.no_avatar_image),
                    contentDescription = stringResource(R.string.no_avatar_description),
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.contact_avatar_size))
                        .clip(MaterialTheme.shapes.medium)
                )
            } else {
                AsyncImage(
                    model = contact.avatarUri,
                    contentDescription = stringResource(R.string.contact_avatar_description) + " " + contact.fullName,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.contact_avatar_size))
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = dimensionResource(R.dimen.small_padding))
            ) {
                Text(
                    text = contact.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}