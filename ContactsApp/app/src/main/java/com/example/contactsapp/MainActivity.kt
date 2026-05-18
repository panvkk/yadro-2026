package com.example.contactsapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.contactsapp.ui.theme.ContactsAppTheme
import com.example.contactsapp.ui.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val contactsViewModel: ContactsViewModel by viewModels()

    private var currentPhoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppTheme {
                ContactsApp(
                    contactsViewModel = contactsViewModel,
                    makeCall = { phoneNumber ->
                        checkPermissionAndCall(this, phoneNumber)
                    })
            }
        }
        checkReadContactsPermission()
    }
    private fun checkReadContactsPermission() {
        val permission = Manifest.permission.READ_CONTACTS
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            contactsViewModel.loadContacts()
        } else {
            requestReadContactsPermissionLauncher.launch(permission)
        }
    }
    private fun checkPermissionAndCall(context: Context, phoneNumber: String) {
        currentPhoneNumber = phoneNumber
        val permission = Manifest.permission.CALL_PHONE
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            makeCall(context, currentPhoneNumber!!)
            currentPhoneNumber = null
        } else {
            requestCallPhonePermissionLauncher.launch(permission)
        }
    }
    private fun makeCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(intent)
        }
    }
    private val requestReadContactsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) contactsViewModel.loadContacts()
    }
    private val requestCallPhonePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val phoneNumber = currentPhoneNumber
        if(isGranted) {
            phoneNumber?.let { makeCall(this, it) }
        }
        currentPhoneNumber = null
    }
}