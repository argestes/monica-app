package com.birohcek.monicaapp.screens.contacts.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.map
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.screens.login.LoadingState

@Composable
fun ContactsScreen(
    navHostController: NavHostController,
    accountId: Long?,
    contactViewModel: ContactsViewModel = viewModel()
) {
    val stateObservable = contactViewModel.getContactsOfId(accountId!!)
    val contacts by stateObservable.map { it.list }.observeAsState()
    val loadingStatus by stateObservable.map { it.loadingState }.observeAsState()
    val error by stateObservable.map { it.errorMessage }.observeAsState()

    val onContactSelected: (ContactModel) -> Unit = { c ->
        navHostController.navigate("account/${accountId}/contacts/${c.id}")
    }

    Surface {
        Column {
            TopAppBar() {
                Text(
                    text = "Contacts",
                    Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp))
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = "Accounts",
                    Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp))
                        .align(Alignment.CenterVertically)
                        .clickable(onClick = { navHostController.navigate("accounts") })
                )


                if (loadingStatus == LoadingState.LOADING) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.padding(PaddingValues(end = 8.dp))
                            .align(Alignment.CenterVertically)
                    )
                }

                if (loadingStatus == LoadingState.ERROR) {
                    Text(
                        text = error ?: "",
                        Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp))
                            .align(Alignment.CenterVertically)
                            .clickable(onClick = { navHostController.navigate("accounts") })
                    )
                }

            }
            ContactList(contacts, onContactSelected)
        }
    }
}

@Composable
private fun ContactListStateless(
    items: List<ContactModel>,
    onContactSelected: (ContactModel) -> Unit
) {
    LazyColumn() {
        items(items) {
            ContactRow(fullname = it.fullName, onClick = { onContactSelected(it) })
        }
    }
}

@Composable
fun ContactList(
    contacts: List<ContactModel>?,
    onContactSelected: (ContactModel) -> Unit
) {
    contacts?.let { ContactListStateless(it, onContactSelected) }
}

@Composable
fun ContactRow(fullname: String, onClick: () -> Unit) {
    ListItem(text = { Text(fullname) }, modifier = Modifier.clickable(onClick = onClick))
}
