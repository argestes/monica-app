package com.birohcek.monicaapp.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.birohcek.monicaapp.models.AccountModel

@Composable
fun AccountsScreen(
    navHostController: NavHostController,
    accountsViewModel: AccountsViewModel
) {
    val accounts by accountsViewModel.getAccounts().asLiveData().observeAsState()

    Surface() {
        Column() {
            TopAppBar() {
                Text(
                    text = "Accounts",
                    Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp)).align(
                        Alignment.CenterVertically
                    )
                )

                Text(
                    text = "Add Account",
                    Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp))
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = { navHostController.navigate("login") })
                )
            }

            Box(modifier = Modifier.padding(PaddingValues(start = 16.dp, end = 16.dp))) {
                accounts?.let {

                    AccountsList(
                        it
                    ) { account -> navHostController.navigate("account/${account.id}/contacts") }
                }
            }
        }
    }
}

@Composable
fun AccountsList(accounts: List<AccountModel>, onAccountSelected: (AccountModel) -> Unit) {

    LazyColumn() {
        items(accounts) { account ->
            ListItem(
                text = { Text(account.username) },
                secondaryText = { Text(account.endpoint) },
                modifier = Modifier.clickable(onClick = {onAccountSelected(account)})
            )

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable(onClick = { onAccountSelected(account) })
//                    .height(48.dp)
//            ) {
//                Text(
//                    text = "${account.username} ${account.endpoint}",
//                    modifier = Modifier.weight(1f, fill = true)
//                )
//            }
        }
    }
}
