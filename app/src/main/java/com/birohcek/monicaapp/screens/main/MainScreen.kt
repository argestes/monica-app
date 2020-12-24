package com.birohcek.monicaapp.screens.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.screens.accounts.AccountsScreen
import com.birohcek.monicaapp.screens.accounts.AccountsViewModel
import com.birohcek.monicaapp.screens.contacts.list.ContactsScreen
import com.birohcek.monicaapp.screens.contacts.list.ContactsViewModel
import com.birohcek.monicaapp.screens.contacts.single.ContactDetailScreen
import com.birohcek.monicaapp.screens.contacts.single.SingleContactViewModel
import com.birohcek.monicaapp.screens.login.LoginScreen
import com.birohcek.monicaapp.screens.login.LoginViewModel
import com.birohcek.monicaapp.screens.splash.Splash
import com.birohcek.monicaapp.ui.monicaTypography


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    accountDao: AccountDao,
    loginViewModel: LoginViewModel,
    contactsViewModel: ContactsViewModel,
    singleContactViewModel: SingleContactViewModel,
    accountsViewModel: AccountsViewModel
) {
    val navController: NavHostController = rememberNavController()

    MaterialTheme(typography = monicaTypography) {


        NavHost(navController, startDestination = "splash") {
            composable("splash") { Splash(navController, accountDao) }
            composable("login") { LoginScreen(navController, loginViewModel) }
            composable(
                "account/{accountId}/contacts",
                arguments = listOf(navArgument("accountId") { type = NavType.LongType })
            ) { backstackEntry ->
                ContactsScreen(
                    navController,
                    backstackEntry.arguments?.getLong("accountId"),
                    contactsViewModel
                )
            }
            composable(
                "account/{accountId}/contacts/{contactId}",
                arguments = listOf(
                    navArgument("accountId") { type = NavType.LongType },
                    navArgument("contactId") { type = NavType.LongType },
                )
            ) { backstackEntry ->

                val accountIdParam = backstackEntry.arguments?.getLong("accountId")!!
                val contactIdParam = backstackEntry.arguments?.getLong("contactId")!!

                ContactDetailScreen(
                    navController,
                    singleContactViewModel,
                    accountIdParam,
                    contactIdParam
                )
            }

            composable("accounts") { AccountsScreen(navController, accountsViewModel) }
        }

    }
}
