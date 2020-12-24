package com.birohcek.monicaapp

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.birohcek.monicaapp.screens.login.LoginViewModel
import com.birohcek.monicaapp.manager.ILoginManager
import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.screens.accounts.AccountsViewModel
import com.birohcek.monicaapp.screens.contacts.list.ContactsViewModel
import com.birohcek.monicaapp.screens.contacts.single.SingleContactViewModel
import com.birohcek.monicaapp.screens.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginManager: ILoginManager

    @Inject
    lateinit var accountDao: AccountDao

    private val loginViewModel: LoginViewModel by viewModels()
    private val contactsViewModel: ContactsViewModel by viewModels()
    private val singleContactViewModel: SingleContactViewModel by viewModels()
    private val accountsViewModel : AccountsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val composeView = findViewById<ComposeView>(R.id.composeview)
        composeView.setContent { MainScreen(
            accountDao,
            loginViewModel,
            contactsViewModel,
            singleContactViewModel,
            accountsViewModel
        ) }
    }
}