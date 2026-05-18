package com.example.contactsapp.data.local

import android.content.Context
import android.provider.ContactsContract
import com.example.contactsapp.core.AppDispatchers
import com.example.contactsapp.data.dto.ContactDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactsDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: AppDispatchers
) {

    fun getContacts() : Flow<List<ContactDto>> = flow {
        val contactsList = mutableListOf<ContactDto>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        )

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
            val phoneIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)

            while (cursor.moveToNext()) {
                contactsList.add(
                    ContactDto(
                        contactId = cursor.getString(idIndex),
                        contactDisplayName = cursor.getString(nameIndex),
                        mainPhoneNumber = cursor.getString(phoneIndex),
                        contactAvatarUri = cursor.getString(photoIndex)
                    )
                )

            }
        }
        emit(contactsList)
    }.flowOn(dispatchers.io)
}