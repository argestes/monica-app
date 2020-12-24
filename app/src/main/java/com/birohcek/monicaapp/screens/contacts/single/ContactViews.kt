package com.birohcek.monicaapp.screens.contacts.single

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.map
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.birohcek.monicaapp.screens.login.LoadingState

@Composable
fun ContactDetailScreen(
    navController: NavHostController,
    singleContactViewModel: SingleContactViewModel = viewModel(),
    account: Long,
    contactId: Long
) {
    val contactLiveData = singleContactViewModel.getContact(account, contactId)

    val loadingStatus by contactLiveData.map { it.loadingState }.observeAsState()
    val data by contactLiveData.map { it.data }.observeAsState()

    Surface() {
        Column() {
            Box(modifier = Modifier.padding(PaddingValues(start = 16.dp, end = 16.dp)).fillMaxSize()) {
                data?.let {
                    SingleContactView(it) { contactId: Long ->
                        openContact(
                            navController,
                            account,
                            contactId
                        )
                    }
                }

                if (loadingStatus == LoadingState.LOADING) {

                    Card(
                        elevation = 6.dp,
                        modifier = Modifier.height(100.dp).width(100.dp).align(Alignment.Center)
                    ) {
                        CircularProgressIndicator(Modifier.height(50.dp).width(50.dp))
                    }

                }
            }
        }
    }
}

fun openContact(navController: NavHostController, account: Long, contactId: Long) {
    navController.navigate("account/${account}/contacts/${contactId}")
}


