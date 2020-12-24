package com.birohcek.monicaapp.screens.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.birohcek.monicaapp.models.AccountModel
import com.birohcek.monicaapp.persistance.AccountDao

@Composable
fun Splash(navHostController: NavHostController, accountDao: AccountDao) {

    val first: State<List<AccountModel>?> = accountDao.getAll().collectAsState(initial = null)

    first.value?.let {
        if (it.isNotEmpty()) {
            val account = it.first()

            navHostController.navigate("account/${account.id}/contacts")
            return
        }
    }

    Surface {
        Box() {
            Text(
                text = "Splash",
                modifier = Modifier.align(Alignment.Center).clickable(onClick = {
                    navHostController.navigate("login")
                })
            )
        }
    }
}
