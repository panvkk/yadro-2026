package com.example.contactsapp.data.local

import android.content.Context
import android.provider.ContactsContract
import com.example.contactsapp.core.AppDispatchers
import com.example.contactsapp.data.dto.ContactDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: AppDispatchers
) {

    suspend fun getContacts() : List<ContactDto> = withContext(dispatchers.io) {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        )

        val contactsMap = HashMap<Long, ContactDto>()

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
            val numberTypeIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idIndex)
                val phoneNumber = cursor.getString(phoneIndex)
                val normalizedPhoneNumber = android.telephony.PhoneNumberUtils.normalizeNumber(phoneNumber)

                val currentIsMobile = cursor.getInt(numberTypeIndex) == 1

                if(contactsMap.contains(id)) {
                    val existingContact = contactsMap[id]!!
                    if(!existingContact.isMobile && currentIsMobile) {
                        contactsMap[id] = existingContact.copy(
                            phoneNumber = normalizedPhoneNumber,
                            isMobile = true
                        )
                    }
                } else {
                    contactsMap[id] = ContactDto(
                        contactId = id,
                        contactDisplayName = cursor.getString(nameIndex),
                        phoneNumber = normalizedPhoneNumber,
                        isMobile = currentIsMobile,
                        contactAvatarUri = cursor.getString(photoIndex)
                    )
                }
            }
        }
        contactsMap.values.toList()
    }
}